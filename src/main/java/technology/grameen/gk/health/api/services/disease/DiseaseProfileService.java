package technology.grameen.gk.health.api.services.disease;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.DiseaseProfile;
import technology.grameen.gk.health.api.entity.DiseaseType;
import technology.grameen.gk.health.api.repositories.lookup.DiseaseProfileRepository;

import java.util.List;

public interface DiseaseProfileService {

    DiseaseProfile addDiseaseProfile(DiseaseProfile diseaseProfile);

    List<DiseaseProfileRepository.DiseaseProfileSimple> getAll(DiseaseType diseaseType);

    List<DiseaseProfileRepository.DiseaseProfileSimple> getAll();
    Page<DiseaseProfileRepository.DiseaseProfileSimple> getAll(Pageable pageable);
}
