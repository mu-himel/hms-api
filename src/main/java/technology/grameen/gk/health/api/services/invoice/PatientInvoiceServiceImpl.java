package technology.grameen.gk.health.api.services.invoice;

import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.projection.PatientInvoiceAutoComplete;
import technology.grameen.gk.health.api.projection.PatientInvoiceDetail;
import technology.grameen.gk.health.api.entity.*;
import technology.grameen.gk.health.api.projection.PrescriptionInvoiceAutoComplete;
import technology.grameen.gk.health.api.repositories.EventRepository;
import technology.grameen.gk.health.api.repositories.PatientInvoiceRepository;
import technology.grameen.gk.health.api.repositories.patient.PatientServiceRepository;
import technology.grameen.gk.health.api.requests.InvoiceCreate;
import technology.grameen.gk.health.api.requests.PatientInvoiceRequest;
import technology.grameen.gk.health.api.services.HealthCenterService;
import technology.grameen.gk.health.api.services.card_registration.CardRegistrationService;
import technology.grameen.gk.health.api.services.event.EventService;
import technology.grameen.gk.health.api.services.patient.PatientManageService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class PatientInvoiceServiceImpl implements PatientInvoiceService {

    private PatientInvoiceRepository invoiceRepository;
    private PatientServiceRepository patientServiceRepository;
    private CardRegistrationService cardRegistrationService;
    private EventService eventService;
    private PatientManageService patientManageService;
    private HealthCenterService healthCenterService;
    private boolean patientNotFound = false;
    private String patientNotFoundMessage = null;

    PatientInvoiceServiceImpl(PatientInvoiceRepository invoiceRepository,
                              PatientServiceRepository patientServiceRepository,
                              CardRegistrationService cardRegistrationService,
                              EventService eventService,
                              PatientManageService patientManageService,
                              HealthCenterService healthCenterService){

        this.invoiceRepository = invoiceRepository;
        this.patientServiceRepository = patientServiceRepository;
        this.cardRegistrationService = cardRegistrationService;
        this.eventService = eventService;
        this.patientManageService = patientManageService;
        this.healthCenterService = healthCenterService;
    }

    @Override
    public Optional<PatientInvoiceDetail> getInvoiceById(Long id) {
        return invoiceRepository.findByInvoiceId(id);
    }

    @Override
    public Optional<PatientInvoiceDetail> getInvoiceByNumber(String number) {
        return invoiceRepository.findByInvoiceNumber(number);
    }

    @Override
    public List<PatientInvoiceAutoComplete> getInvoiceByNumbers(String number) {
        return invoiceRepository.findByInvoiceNumberContainingIgnoreCase(number);
    }

    @Override
    public List<PrescriptionInvoiceAutoComplete> getPrescriptionInvoiceByNumber(Integer centerId,
                                                                                Long employeeId) {


        List<PrescriptionInvoiceAutoComplete> invoices = null;
        if(employeeId>0){
            invoices = invoiceRepository.getCampPrescriptionInvoiceNumbersByDoctor(employeeId);
        }
        if(invoices.size()>0) {
            return invoices;
        }

        return invoiceRepository.getPrescriptionInvoiceNumbers(centerId);
    }

    @Override
    public List<PrescriptionInvoiceAutoComplete> getLabTestInvoiceByNumbers(Integer centerId) {
        return invoiceRepository.getLabTestInvoiceNumbers(centerId);
    }

    @Override
    public List<PrescriptionInvoiceAutoComplete> getLabTestInvoiceByCenterAndRole(Optional<Integer> officeTypeId,
                                                                                  Optional<Long> centerId,
                                                                                  Optional<String> role) {

        if(officeTypeId.isPresent() && centerId.isPresent() && role.isPresent()) {
            if(officeTypeId.get() == 5 && role.get().toLowerCase().contains("sono")) {
               Optional<HealthCenter> healthCenterOptional = healthCenterService.findById(centerId.get());
               if(healthCenterOptional.isPresent()) {
                   List<Long> centerIds = healthCenterService.getCenterIdByThirdLevel(healthCenterOptional
                                                                                                .get()
                                                                                                .getThirdLevel());
                   return invoiceRepository.getLabTestInvoiceNumbersForUsg(centerIds);
               }
            }else{
               return getLabTestInvoiceByNumbers(centerId.get().intValue());
            }
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public PatientInvoice createInvoice(InvoiceCreate patient) throws Exception {
        this.patientNotFound = false;
        PatientInvoiceRequest pi = patient.getPatientInvoices()
                    .stream().filter(invoice-> invoice.getId()==null)
                            .findFirst().orElse(null);

        PatientInvoice patientInvoice = new PatientInvoice();
        Patient pat = new Patient();
        pat.setId(patient.getId());
        pat.setBloodPressure(patient.getBloodPressure());
        pat.setPulse(patient.getPulse());
        pat.setWeight(patient.getWeight());
        pat.setTemperature(patient.getTemperature());
        pat.setRegistration(patient.getRegistration());
        pat.setCenter(patient.getCenter());
        patientInvoice.setPatient(pat);
        patientInvoice.setInvoiceType(pi.getInvoiceType());


        int maxInvoiceId = (invoiceRepository.getMaxInvoiceId()!=null)? invoiceRepository.getMaxInvoiceId()+1 : 1;
        String invoiceId = "INV-"+patient.getPid()+"-"+((maxInvoiceId<9)? "0"+maxInvoiceId : maxInvoiceId);
        patientInvoice.setInvoiceNumber(invoiceId);

        patientInvoice.setDiscountAmount(pi.getDiscountAmount());
        patientInvoice.setDueAmount(pi.getDueAmount());
        patientInvoice.setServiceAmount(pi.getServiceAmount());
        patientInvoice.setPayableAmount(pi.getPayableAmount());
        patientInvoice.setPaidAmount(pi.getPaidAmount());
        patientInvoice.setEvent(pi.getEvent());

        HealthCenter center = patient.getCenter();
        Employee employee = patient.getCreatedBy();


        center.addPatientInvoices(patientInvoice);


        employee.addPatientInvoice(patientInvoice);

        invoiceRepository.save(patientInvoice);

        if(patientInvoice.getId()>0){

            EventRepository.EventEventEmployeeByInvoice eventEventEmployeeByInvoice = eventService
                    .getEventByInvoiceId(patientInvoice.getId()).orElse(null);

            List<PatientServiceDetail> patientServiceDetails = pi.getPatientServiceDetails();
            patientServiceDetails.forEach(patientServiceDetail->{

                patientServiceDetail.setServiceQty(1);
                patientServiceDetail.setReportGenerated(false);

                if(eventEventEmployeeByInvoice!=null && patientInvoice.getInvoiceType().toLowerCase().contains("camp")){
                    patientServiceDetail.setTreatmentBy(new Employee(eventEventEmployeeByInvoice.getEmployeeId()));
                }
                Service service = patientServiceDetail.getService();
                service.addPatientService(patientServiceDetail);
                patientInvoice.addPatientServiceDetail(patientServiceDetail);
                patientServiceRepository.save(patientServiceDetail);

                if(patientServiceDetail.getService().getCode().contains("prescription")){
                    patientManageService.updatePatient(pat);
                }

                if(patientServiceDetail.getService().getCode().contains("card") ||
                        patientServiceDetail.getService().getCode().contains("card registration") ){

                    try {
                        cardRegistrationService.register(pat);
                    } catch (Exception e) {
                        this.patientNotFound = true;
                        this.patientNotFoundMessage = e.getMessage();
                        return;
                    }
                }
            });

            if(this.patientNotFound){
                throw new Exception(this.patientNotFoundMessage);
            }

        }

        return patientInvoice;
    }

    @Override
    public Optional<PatientServiceDetail> getPatientServiceDetailByInvoiceAndService(
                            PatientInvoice patientInvoice, Service service) {
        return patientServiceRepository.findByPatientInvoiceAndService(patientInvoice,service);
    }

    @Override
    public PatientServiceDetail updatePatientServiceDetail(PatientServiceDetail patientServiceDetail) {
        return patientServiceRepository.save(patientServiceDetail);
    }

    @Override
    public Optional<PatientServiceDetail> getPrescriptionServiceDetailByPatientInvoice(PatientInvoice patientInvoice) {
        Optional<PatientServiceDetail> _patientServiceDetail =null;
            Long patientInvoiceId = patientInvoice.getId();

            Optional<PatientInvoiceDetail> patientInvoiceDetail = getInvoiceById(patientInvoiceId);

            if (patientInvoiceDetail.isPresent()) {
                PatientInvoiceDetail _patientInvoice = patientInvoiceDetail.get();
                Optional<PatientInvoiceDetail.PatientServiceDetail> patientServiceDetailOptional = _patientInvoice
                        .getPatientServiceDetails()
                        .stream()
                        .filter(patientServiceDetail -> patientServiceDetail.getService()
                                .getName().toLowerCase().contains("prescription") ||
                                patientServiceDetail.getService()
                                        .getName().toLowerCase().contains("doctor"))
                        .findFirst();

                if (patientServiceDetailOptional.isPresent()) {

                    _patientServiceDetail = patientServiceRepository.findById(patientServiceDetailOptional.get().getId());

                }
            }

        return _patientServiceDetail;
    }

    @Override
    public Optional<BigDecimal> getTotalUnPostedAmount() {
        return invoiceRepository.getTotalUnPostedAmount();
    }

    @Override
    public Integer postInvoice() {
        return invoiceRepository.postInvoice();
    }

    @Override
    public Optional<?> refund(PatientServiceDetail detail) throws CustomException {

        Long psdId = detail.getId();
        Long invoiceId = detail.getPatientInvoice().getId();

        Optional<PatientServiceDetail> serviceDetailOp = patientServiceRepository.findById(psdId);
        Optional<PatientInvoice> invoiceOp = invoiceRepository.findById(invoiceId);

        if(!invoiceOp.isPresent()){
            throw new CustomException("Sorry! Invoice not found");
        }

        if(invoiceOp.isPresent()) {
            PatientInvoice patientInvoice = invoiceOp.get();
            if(patientInvoice.getPosted()){
                throw new CustomException("Sorry! Operation Denied, Invoice Already posted");
            }
            if (serviceDetailOp.isPresent()) {

                PatientServiceDetail psd = serviceDetailOp.get();

                if(psd.getReportGenerated()){
                    throw new CustomException("Sorry! Service Already given. Refund not applicable for the service");
                }

                psd.setRefunded(true);
                patientServiceRepository.save(psd);
                patientInvoice.setPayableAmount(patientInvoice.getPayableAmount().subtract(psd.getPayableAmount()));
                patientInvoice.setServiceAmount(patientInvoice.getServiceAmount().subtract(psd.getServiceAmount()));
                patientInvoice.setDiscountAmount(patientInvoice.getDiscountAmount().subtract(psd.getDiscountAmount()));
                patientInvoice.setPaidAmount(patientInvoice.getPaidAmount().subtract(psd.getPayableAmount()));
                invoiceRepository.save(patientInvoice);
            }

        }

        return invoiceRepository.findByInvoiceId(invoiceId);
    }
}
