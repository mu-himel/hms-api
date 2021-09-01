package technology.grameen.gk.health.api.services.disease;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.entity.DiseaseProfile;
import technology.grameen.gk.health.api.entity.DiseaseType;
import technology.grameen.gk.health.api.repositories.DiseaseProfileRepository;

import java.util.List;

@Service
public class DiseaseProfileServiceImpl implements DiseaseProfileService{

    private DiseaseProfileRepository diseaseProfileRepository;

    public DiseaseProfileServiceImpl(DiseaseProfileRepository diseaseProfileRepository) {
        this.diseaseProfileRepository = diseaseProfileRepository;
    }


    @Override
    public DiseaseProfile addDiseaseProfile(DiseaseProfile diseaseProfile) {
        return diseaseProfileRepository.save(diseaseProfile);
    }

    @Override
    public List<DiseaseProfileRepository.DiseaseProfileSimple> getAll(DiseaseType diseaseType) {
        return diseaseProfileRepository.findByDiseaseType(diseaseType);
    }
}
