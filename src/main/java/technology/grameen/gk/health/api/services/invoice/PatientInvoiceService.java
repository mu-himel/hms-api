package technology.grameen.gk.health.api.services.invoice;

import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.entity.PatientInvoice;
import technology.grameen.gk.health.api.entity.PatientServiceDetail;
import technology.grameen.gk.health.api.entity.Service;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.projection.PatientInvoiceAutoComplete;
import technology.grameen.gk.health.api.projection.PatientInvoiceDetail;
import technology.grameen.gk.health.api.projection.PrescriptionInvoiceAutoComplete;
import technology.grameen.gk.health.api.requests.InvoiceCreate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PatientInvoiceService {

    PatientInvoice createInvoice(InvoiceCreate patient) throws Exception;

    Optional<PatientInvoiceDetail> getInvoiceById(Long id);
    Optional<PatientInvoiceDetail> getInvoiceByNumber(String number);

    List<PatientInvoiceAutoComplete> getInvoiceByNumbers(String number);
    List<PrescriptionInvoiceAutoComplete> getPrescriptionInvoiceByNumber(Integer centerId,
                                                                         Long employeeId);

    Optional<PatientServiceDetail> getPatientServiceDetailByInvoiceAndService(PatientInvoice patientInvoice,
                                                                              Service service);

    PatientServiceDetail updatePatientServiceDetail(PatientServiceDetail patientServiceDetail);

    Optional<PatientServiceDetail> getPrescriptionServiceDetailByPatientInvoice(PatientInvoice patientInvoice);

    Optional<BigDecimal> getTotalUnPostedAmount();

    Integer postInvoice();

    List<PrescriptionInvoiceAutoComplete> getLabTestInvoiceByNumbers(Integer centerId);

    Optional<?> refund(PatientServiceDetail detail) throws CustomException;
}
