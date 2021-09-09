package technology.grameen.gk.health.api.services.disease;

import technology.grameen.gk.health.api.entity.DiseaseProfile;
import technology.grameen.gk.health.api.entity.DiseaseType;
import technology.grameen.gk.health.api.repositories.DiseaseProfileRepository;

import java.util.List;

public interface DiseaseProfileService {

    DiseaseProfile addDiseaseProfile(DiseaseProfile diseaseProfile);

    List<DiseaseProfileRepository.DiseaseProfileSimple> getAll(DiseaseType diseaseType);

    List<DiseaseProfileRepository.DiseaseProfileSimple> getAll();
}
