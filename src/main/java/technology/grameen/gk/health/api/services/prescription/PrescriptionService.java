package technology.grameen.gk.health.api.services.prescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.entity.PatientInvoice;
import technology.grameen.gk.health.api.entity.Prescription;
import technology.grameen.gk.health.api.projection.PrescriptionDetail;
import technology.grameen.gk.health.api.projection.PrescriptionListItem;

import java.util.List;
import java.util.Optional;

public interface PrescriptionService {

    Prescription savePrescription(Prescription prescription);

    Page<PrescriptionListItem> getPrescriptions(Pageable pageable);
    Page<PrescriptionListItem> getPrescriptions(String pNumber,String fullName, String date, Pageable pageable);
    Page<PrescriptionListItem> getPrescriptions(String regionCode, String centerCode,String pNumber,String fullName, String date, Pageable pageable);

    Optional<PrescriptionDetail> getPrescriptionById(Long id);

    Optional<PrescriptionDetail> getPrescriptionByPatientAndInvoice(Patient patient, PatientInvoice invoice);
}
