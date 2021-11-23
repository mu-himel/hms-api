package technology.grameen.gk.health.api.services.prescription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.*;
import technology.grameen.gk.health.api.projection.PatientInvoiceDetail;
import technology.grameen.gk.health.api.projection.PrescriptionDetail;
import technology.grameen.gk.health.api.projection.PrescriptionListItem;
import technology.grameen.gk.health.api.repositories.*;
import technology.grameen.gk.health.api.services.invoice.PatientInvoiceService;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private static final Logger logger = LoggerFactory.getLogger(PrescriptionServiceImpl.class);

    private PrescriptionRepository prescriptionRepository;
    private FamilyHistoryRepository familyHistoryRepository;
    private PersonalHistoryRepository personalHistoryRepository;
    private GeneralExaminationRepository generalExaminationRepository;
    private RecommendedTestRepository recommendedTestRepository;
    private RecommendedMedicineRepository recommendedMedicineRepository;
    private PatientInvoiceService patientInvoiceService;

    PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                            FamilyHistoryRepository familyHistoryRepository,
                            PersonalHistoryRepository personalHistoryRepository,
                            GeneralExaminationRepository generalExaminationRepository,
                            RecommendedTestRepository recommendedTestRepository,
                            RecommendedMedicineRepository recommendedMedicineRepository,
                            PatientInvoiceService patientInvoiceService){

        this.prescriptionRepository = prescriptionRepository;
        this.familyHistoryRepository = familyHistoryRepository;
        this.personalHistoryRepository = personalHistoryRepository;
        this.generalExaminationRepository = generalExaminationRepository;
        this.recommendedTestRepository = recommendedTestRepository;
        this.recommendedMedicineRepository = recommendedMedicineRepository;
        this.patientInvoiceService = patientInvoiceService;
    }

    @Override
    @Transactional
    public Prescription savePrescription(Prescription prescription) {

        if(prescription.getId()==null) {
            prescription.setpNumber(getPrescriptionNumber(prescription.getCenter()));
            LocalDateTime localDateTime = LocalDateTime.now();
            if (prescription.getId() == null) {
                localDateTime = localDateTime.plusDays(7);
            }
            prescription.setLastFreeVisitDate(localDateTime);
        }

        FamilyHistory familyHistory = prescription.getFamilyHistory();
        PersonalHistory personalHistory = prescription.getPersonalHistory();
        GeneralExamination generalExamination = prescription.getGeneralExamination();

        if(prescription.getId()!=null){
            familyHistory.setPrescription(prescription);
            personalHistory.setPrescription(prescription);
            generalExamination.setPrescription(prescription);
        }
        Prescription newPrescription = prescriptionRepository.save(prescription);




        if(newPrescription.getId()>0) {

            if(familyHistory.getId()==null) {
                familyHistory.setPrescription(newPrescription);
            }

            if(personalHistory.getId() == null){
                personalHistory.setPrescription(newPrescription);
            }

            if(generalExamination.getId()==null){
                generalExamination.setPrescription(newPrescription);
            }

            familyHistoryRepository.save(familyHistory);
            personalHistoryRepository.save(personalHistory);
            generalExaminationRepository.save(generalExamination);

            if (prescription.getRecommendedTests().size() > 0) {
                prescription.getRecommendedTests().stream()
                        .map((recommendedTest) -> {
                            recommendedTest.setPrescription(prescription);
                            return recommendedTest;
                        }).collect(Collectors.toSet());
                recommendedTestRepository.saveAll(prescription.getRecommendedTests());
            }

            if (prescription.getRecommendedMedicines().size() > 0) {
                prescription.getRecommendedMedicines().stream()
                        .map((recommendedMedicine) -> {
                            recommendedMedicine.setPrescription(prescription);
                            return recommendedMedicine;
                        }).collect(Collectors.toSet());

                recommendedMedicineRepository.saveAll(prescription.getRecommendedMedicines());
            }


            Optional<PatientServiceDetail> _patientServiceDetail=null;

            _patientServiceDetail = patientInvoiceService
                    .getPrescriptionServiceDetailByPatientInvoice(prescription.getPatientInvoice());
            if(_patientServiceDetail != null && _patientServiceDetail.isPresent()) {
                PatientServiceDetail patientServiceDetail = _patientServiceDetail.get();
                patientServiceDetail.setReportGenerated(true);
                patientInvoiceService.updatePatientServiceDetail(patientServiceDetail);
            }

        }

        return newPrescription;
    }

    @Override
    public Page<PrescriptionListItem> getPrescriptions(Pageable pageable) {
        return prescriptionRepository.findAllPrescriptions(pageable);
    }

    @Override
    public Page<PrescriptionListItem> getPrescriptions(String pNumber, String fullName, String date, Pageable pageable) {
        if(pNumber.isEmpty() && fullName.isEmpty() && date.isEmpty()){
            return prescriptionRepository.findAllPrescriptions(pageable);
        }
        if(!pNumber.isEmpty() && fullName.isEmpty() && date.isEmpty()){
            return prescriptionRepository.findAllPrescriptionsByPNumber(pNumber,pageable);
        }
        if(pNumber.isEmpty() && !fullName.isEmpty() && date.isEmpty()){
            return prescriptionRepository.findAllPrescriptionsByFullName(fullName,pageable);
        }
        if(pNumber.isEmpty() && fullName.isEmpty() && !date.isEmpty()){
            return prescriptionRepository.findAllPrescriptionsByDate(date,pageable);
        }
        return prescriptionRepository.findAllPrescriptions(pNumber, fullName, date, pageable);
    }

    @Override
    public Page<PrescriptionListItem> getPrescriptions(String regionCode, String centerCode, String pNumber, String fullName, String date, Pageable pageable) {
        Page<PrescriptionListItem> result = null;
        if(!regionCode.isEmpty() && centerCode.isEmpty()){
            if(pNumber.isEmpty() && fullName.isEmpty() && date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptions(regionCode,pageable);
            }
            if(!pNumber.isEmpty() && fullName.isEmpty() && date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptionsByPNumber(regionCode,pNumber,pageable);
            }
            if(pNumber.isEmpty() && !fullName.isEmpty() && date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptionsByFullName(regionCode,fullName,pageable);
            }
            if(pNumber.isEmpty() && fullName.isEmpty() && !date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptionsByDate(regionCode,date,pageable);
            }
        }else if(regionCode.isEmpty() && !centerCode.isEmpty()){
            if(pNumber.isEmpty() && fullName.isEmpty() && date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptionsByCenter(centerCode,pageable);
            }
            if(!pNumber.isEmpty() && fullName.isEmpty() && date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptionsByPNumberByCenter(centerCode,pNumber,pageable);
            }
            if(pNumber.isEmpty() && !fullName.isEmpty() && date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptionsByFullNameByCenter(centerCode,fullName,pageable);
            }
            if(pNumber.isEmpty() && fullName.isEmpty() && !date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptionsByDateByCenter(centerCode,date,pageable);
            }
        }else{
            if(pNumber.isEmpty() && fullName.isEmpty() && date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptions(pageable);
            }
            if(!pNumber.isEmpty() && fullName.isEmpty() && date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptionsByPNumber(pNumber,pageable);
            }
            if(pNumber.isEmpty() && !fullName.isEmpty() && date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptionsByFullName(fullName,pageable);
            }
            if(pNumber.isEmpty() && fullName.isEmpty() && !date.isEmpty()){
                result = prescriptionRepository.findAllPrescriptionsByDate(date,pageable);
            }
        }

        return result;
    }

    @Transactional
    String getPrescriptionNumber(HealthCenter center){
        Calendar calendar = Calendar.getInstance();
        int year = (calendar.get(Calendar.YEAR));
        int month = (calendar.get(Calendar.MONTH));
        int date = (calendar.get(Calendar.DATE));
        Long maxId = 0L;
        try {
            maxId = prescriptionRepository.getMaxId(center.getId());
            maxId++;
        }catch(Exception ex){
            logger.error(ex.getLocalizedMessage());
        }
        return center.getCenterCode()+"-"+ year + (((month+1)<10)? "0"+(month+1) :
                (month+1)) + ((date<10)? "0"+date : date)+"-"+maxId;
    }

    @Override
    public Optional<PrescriptionDetail> getPrescriptionById(Long id) {
        return prescriptionRepository.findByPatientId(id);
    }

    @Override
    public Optional<PrescriptionDetail> getPrescriptionByPatientAndInvoice(Patient patientId, PatientInvoice invoiceId) {
        return prescriptionRepository.findByPrescriptionPatientAndPatientInvoice(patientId, invoiceId);
    }
}
