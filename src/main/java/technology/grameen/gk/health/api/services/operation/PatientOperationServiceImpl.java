package technology.grameen.gk.health.api.services.operation;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.entity.PatientOperation;
import technology.grameen.gk.health.api.repositories.PatientOperationRepository;

@Service
public class PatientOperationServiceImpl implements PatientOperationService{

    private PatientOperationRepository patientOperationRepository;

    public PatientOperationServiceImpl(PatientOperationRepository patientOperationRepository) {
        this.patientOperationRepository = patientOperationRepository;
    }

    @Override
    public PatientOperation saveOperation(PatientOperation patientOperation) {
        return patientOperationRepository.save(patientOperation);
    }
}
