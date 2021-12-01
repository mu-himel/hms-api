package technology.grameen.gk.health.api.services.labtest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.*;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.projection.LabTestDetailItem;
import technology.grameen.gk.health.api.projection.LabTestListItem;
import technology.grameen.gk.health.api.repositories.LabTestRepository;
import technology.grameen.gk.health.api.repositories.lookup.HealthCenterRepository;
import technology.grameen.gk.health.api.repositories.lookup.ServiceRepository;
import technology.grameen.gk.health.api.services.HealthCenterService;
import technology.grameen.gk.health.api.services.invoice.PatientInvoiceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LabTestServiceImpl implements LabTestService {

    private LabTestRepository labTestRepository;
    private LabTestResultService resultService;
    private PatientInvoiceService patientInvoiceService;
    private ServiceRepository serviceRepository;
    private HealthCenterService centerService;

    LabTestServiceImpl(LabTestRepository labTestRepository,
                       LabTestResultService resultService,
                       PatientInvoiceService patientInvoiceService,
                       ServiceRepository serviceRepository,
                       HealthCenterService centerService){
        this.labTestRepository = labTestRepository;
        this.resultService = resultService;
        this.patientInvoiceService = patientInvoiceService;
        this.serviceRepository = serviceRepository;
        this.centerService = centerService;
    }

    @Override
    @Transactional
    public LabTest saveLabTest(LabTest labTest) {

        List<Optional<PatientServiceDetail>> patientServiceDetailSingles = new ArrayList<>();

        labTest.getServices().forEach(s->{
            Optional<PatientServiceDetail> patientServiceDetailSingle = patientInvoiceService
                    .getPatientServiceDetailByInvoiceAndService(labTest.getPatientInvoice(),
                           s);

        if(!patientServiceDetailSingle.isPresent())
        {
            throw new RuntimeException("Sorry! Relevant invoice details not found for service "+
                    s.getName());
        }

            patientServiceDetailSingles.add(patientServiceDetailSingle);
        });

        Set<LabTestDetail> details = labTest.getDetails();
        Set<technology.grameen.gk.health.api.entity.Service> services = labTest.getServices();


        LabTest labTest1 = labTestRepository.save(labTest);
        services.forEach(s->{
            Optional<technology.grameen.gk.health.api.entity.Service> serviceOp = serviceRepository.findById(s.getServiceId());
            if(serviceOp.isPresent()){
                technology.grameen.gk.health.api.entity.Service service = serviceOp.get();
                service.addLabTest(labTest1);
            }
        });


        if(labTest1.getId() > 0){
            details.stream().map( d->{
                d.setLabTest(labTest1);
                return d;
            }).collect(Collectors.toSet());
            resultService.saveAll(details);

            patientServiceDetailSingles.forEach(psd->{
                PatientServiceDetail patientServiceDetail = psd.get();
                patientServiceDetail.setReportGenerated(true);
                patientInvoiceService.updatePatientServiceDetail(patientServiceDetail);
            });

        }
        return labTest1;
    }

    @Override
    public Page<LabTestListItem> getLabTestReports(Pageable pageable) {
        return labTestRepository.getLabTests(pageable);
    }

    @Override
    public Page<LabTestListItem> getLabTestReports(String invoiceNumber,
                                                   String fullName,
                                                   String pid,
                                                   String status,
                                                   Long officeId,
                                                   Short typeId,
                                                   Pageable pageable) {

        HealthCenter region = null;
        List<Long> centerIds = new ArrayList<>();
        if(typeId == 5) {
         Optional<HealthCenter> opRegion =   centerService.findById(officeId);
         if(opRegion.isPresent()) {
             region = opRegion.get();
             centerIds = centerService.getCenterIdByThirdLevel(region.getThirdLevel());
         }
        }

        if(invoiceNumber.isEmpty() && fullName.isEmpty() && pid.isEmpty() && status.isEmpty()){
            if(typeId==1) {
                return labTestRepository.getLabTests(pageable);
            }else if(typeId==5){
                return labTestRepository.getLabTests(centerIds,pageable);
            }else if(typeId==6){
                return labTestRepository.getLabTests(officeId,pageable);
            }
        }
        if(!invoiceNumber.isEmpty() && fullName.isEmpty() && pid.isEmpty() && status.isEmpty()) {
            if(typeId==1) {
                return labTestRepository.getLabTestsByInvoiceNumber(invoiceNumber, pageable);
            }else if(typeId==5){
                return labTestRepository.getLabTestsByInvoiceNumber(centerIds,invoiceNumber, pageable);
            }else if(typeId == 6){
                return labTestRepository.getLabTestsByInvoiceNumber(officeId,invoiceNumber, pageable);
            }
        }
        if(invoiceNumber.isEmpty() && !fullName.isEmpty() && pid.isEmpty() && status.isEmpty()) {
            if(typeId == 1) {
                return labTestRepository.getLabTestByFullName(fullName, pageable);
            }else if(typeId==5){
                return labTestRepository.getLabTestByFullName(centerIds,fullName, pageable);
            }else if(typeId == 6){
                return labTestRepository.getLabTestByFullName(officeId,fullName, pageable);
            }
        }
        if(invoiceNumber.isEmpty() && fullName.isEmpty() && !pid.isEmpty() && status.isEmpty()) {
            if(typeId == 1) {
                return labTestRepository.getLabTestsByPid(pid, pageable);
            }else if(typeId == 5){
                return labTestRepository.getLabTestsByPid(centerIds,pid, pageable);
            }else if(typeId == 6){
                return labTestRepository.getLabTestsByPid(officeId,pid, pageable);
            }
        }
        if(invoiceNumber.isEmpty() && fullName.isEmpty() && pid.isEmpty() && !status.isEmpty()){
            if(typeId == 1) {
                return labTestRepository.findAllByStatus(status, pageable);
            }else if(typeId == 5){
                return labTestRepository.findAllByStatus(centerIds,status, pageable);
            }else if(typeId == 6){
                return labTestRepository.findAllByStatus(officeId,status, pageable);
            }
        }
        Page<LabTestListItem> lists = null;
        if(typeId==1){
            lists = labTestRepository.getLabTests(invoiceNumber,fullName,pid,pageable);
        }else if(typeId == 5){
            lists = labTestRepository.getLabTests(centerIds,invoiceNumber,fullName,pid,pageable);
        }else if(typeId == 6){
            lists = labTestRepository.getLabTests(officeId,invoiceNumber,fullName,pid,pageable);
        }
        return lists;
    }

    @Override
    public Optional<LabTestDetailItem> getLabTestReportById(Long id) {
        return labTestRepository.findByLabTest(id);
    }

    @Override
    public Optional<LabTestDetailItem> getLabTestReportByPatientInvoiceService(
            Patient patient, PatientInvoice patientInvoice,
            technology.grameen.gk.health.api.entity.Service service) {
        return labTestRepository.findByPatientAndPatientInvoice(patient, patientInvoice);
    }
}
