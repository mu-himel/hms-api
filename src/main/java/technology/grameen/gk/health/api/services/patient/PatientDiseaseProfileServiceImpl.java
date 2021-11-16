package technology.grameen.gk.health.api.services.patient;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.PatientDiseaseProfile;
import technology.grameen.gk.health.api.repositories.patient.PatientDiseaseProfileRepository;

import java.util.List;

@Service
public class PatientDiseaseProfileServiceImpl implements PatientDiseaseProfileService{

    private PatientDiseaseProfileRepository diseaseProfileRepository;

    public PatientDiseaseProfileServiceImpl(PatientDiseaseProfileRepository diseaseProfileRepository) {
        this.diseaseProfileRepository = diseaseProfileRepository;
    }

    @Override
    @Transactional
    public PatientDiseaseProfile addDiseaseProfile(PatientDiseaseProfile diseaseProfile) {
        return diseaseProfileRepository.save(diseaseProfile);
    }

    @Override
    public List<?> getDiseaseProfileByPatient(Long id) {
        return diseaseProfileRepository.findByPatientId(id);
    }
}
