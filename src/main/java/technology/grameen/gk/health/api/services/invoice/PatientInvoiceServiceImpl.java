package technology.grameen.gk.health.api.services.invoice;

import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.projection.PatientInvoiceAutoComplete;
import technology.grameen.gk.health.api.projection.PatientInvoiceDetail;
import technology.grameen.gk.health.api.entity.*;
import technology.grameen.gk.health.api.projection.PrescriptionInvoiceAutoComplete;
import technology.grameen.gk.health.api.repositories.PatientInvoiceRepository;
import technology.grameen.gk.health.api.repositories.PatientServiceRepository;
import technology.grameen.gk.health.api.services.card_registration.CardRegistrationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@org.springframework.stereotype.Service
public class PatientInvoiceServiceImpl implements PatientInvoiceService {

    private PatientInvoiceRepository invoiceRepository;
    private PatientServiceRepository patientServiceRepository;
    private CardRegistrationService cardRegistrationService;
    private boolean patientNotFound = false;
    private String patientNotFoundMessage = null;

    PatientInvoiceServiceImpl(PatientInvoiceRepository invoiceRepository,
                              PatientServiceRepository patientServiceRepository,
                              CardRegistrationService cardRegistrationService){

        this.invoiceRepository = invoiceRepository;
        this.patientServiceRepository = patientServiceRepository;
        this.cardRegistrationService = cardRegistrationService;
    }

    @Override
    public Optional<PatientInvoiceDetail> getInvoiceById(Long id) {
        return invoiceRepository.findByInvoiceId(id);
    }

    @Override
    public List<PatientInvoiceAutoComplete> getInvoiceByNumber(String number) {
        return invoiceRepository.findByInvoiceNumberContainingIgnoreCase(number);
    }

    @Override
    public List<PrescriptionInvoiceAutoComplete> getPrescriptionInvoiceByNumber(Integer centerId) {
        return invoiceRepository.getPrescriptionInvoiceNumbers(centerId);
    }

    @Override
    public List<PrescriptionInvoiceAutoComplete> getLabTestInvoiceByNumbers(Integer centerId) {
        return invoiceRepository.getLabTestInvoiceNumbers(centerId);
    }

    @Override
    @Transactional
    public PatientInvoice createInvoice(Patient patient) throws Exception {
        this.patientNotFound = false;
        PatientInvoice patientInvoice = patient.getPatientInvoices()
                    .stream().filter(invoice-> invoice.getId()==null)
                            .findFirst().orElse(null);

        int maxInvoiceId = (invoiceRepository.getMaxInvoiceId()!=null)? invoiceRepository.getMaxInvoiceId()+1 : 1;
        String invoiceId = "INV-"+patient.getPid()+"-"+((maxInvoiceId<9)? "0"+maxInvoiceId : maxInvoiceId);
        patientInvoice.setInvoiceNumber(invoiceId);

        HealthCenter center = patient.getCenter();
        Employee employee = patient.getCreatedBy();

        center.addPatientInvoices(patientInvoice);
        patient.addPatientInvoices(patientInvoice);

        employee.addPatientInvoice(patientInvoice);

        invoiceRepository.save(patientInvoice);

        if(patientInvoice.getId()>0){

            Set<PatientServiceDetail> patientServiceDetails = patientInvoice.getPatientServiceDetails();
            patientServiceDetails.stream().forEach(patientServiceDetail->{

                patientServiceDetail.setServiceQty(1);
                patientServiceDetail.setReportGenerated(false);
                Service service = patientServiceDetail.getService();
                service.addPatientService(patientServiceDetail);
                patientInvoice.addPatientServiceDetail(patientServiceDetail);
                patientServiceRepository.save(patientServiceDetail);

                if(patientServiceDetail.getService().getCode().contains("card") ||
                        patientServiceDetail.getService().getCode().contains("card registration") ){

                    try {
                        cardRegistrationService.register(patient);
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
}
