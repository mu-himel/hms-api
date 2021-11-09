package technology.grameen.gk.health.api.services.operation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.PatientOperation;
import technology.grameen.gk.health.api.repositories.patient.PatientOperationRepository;

public interface PatientOperationService {

    PatientOperation saveOperation(PatientOperation patientOperation);

    Page<PatientOperationRepository.PatientOperation> getOperations(Pageable pageable);
}
