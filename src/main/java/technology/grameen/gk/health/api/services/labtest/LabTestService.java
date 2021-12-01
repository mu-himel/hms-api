package technology.grameen.gk.health.api.services.labtest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.*;
import technology.grameen.gk.health.api.projection.LabTestDetailItem;
import technology.grameen.gk.health.api.projection.LabTestListItem;

import java.util.List;
import java.util.Optional;

public interface LabTestService {

    LabTest saveLabTest(LabTest labTest);

    Page<LabTestListItem> getLabTestReports(Pageable pageable);

    Page<LabTestListItem> getLabTestReports(String invoiceNumber,
                                            String fullName,
                                            String pid,
                                            String status,
                                            Long officeId,
                                            Short typeId,
                                            Pageable pageable);

    Optional<LabTestDetailItem> getLabTestReportById(Long id);

    Optional<LabTestDetailItem> getLabTestReportByPatientInvoiceService(Patient patient,
                                                                 PatientInvoice patientInvoice,
                                                                 Service service);
}
