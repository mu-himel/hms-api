package technology.grameen.gk.health.api.services.patient;

import technology.grameen.gk.health.api.entity.PatientDiseaseProfile;

import java.util.List;

public interface PatientDiseaseProfileService {

    PatientDiseaseProfile addDiseaseProfile(PatientDiseaseProfile diseaseProfile);
    List<PatientDiseaseProfile> addDiseaseProfiles(List<PatientDiseaseProfile> diseaseProfile);
    List<?> getDiseaseProfileByPatient(Long id);
    Boolean deleteById(Long id);
}
