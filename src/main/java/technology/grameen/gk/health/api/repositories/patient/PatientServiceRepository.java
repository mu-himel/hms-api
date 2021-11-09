package technology.grameen.gk.health.api.repositories.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.PatientInvoice;
import technology.grameen.gk.health.api.entity.PatientServiceDetail;
import technology.grameen.gk.health.api.entity.Service;

import java.util.Optional;

@Repository
public interface PatientServiceRepository extends JpaRepository<PatientServiceDetail,Long> {

    Optional<PatientServiceDetail> findByPatientInvoiceAndService(PatientInvoice patientInvoice,
                                                                  Service service);
}
