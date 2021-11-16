package technology.grameen.gk.health.api.services.disease;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.DiseaseProfile;
import technology.grameen.gk.health.api.entity.DiseaseType;
import technology.grameen.gk.health.api.repositories.lookup.DiseaseProfileRepository;

import java.util.List;

@Service
public class DiseaseProfileServiceImpl implements DiseaseProfileService{

    private DiseaseProfileRepository diseaseProfileRepository;

    public DiseaseProfileServiceImpl(DiseaseProfileRepository diseaseProfileRepository) {
        this.diseaseProfileRepository = diseaseProfileRepository;
    }


    @Override
    @Transactional
    public DiseaseProfile addDiseaseProfile(DiseaseProfile diseaseProfile) {
        return diseaseProfileRepository.save(diseaseProfile);
    }

    @Override
    public List<DiseaseProfileRepository.DiseaseProfileSimple> getAll(DiseaseType diseaseType) {
        return diseaseProfileRepository.findByDiseaseType(diseaseType);
    }

    @Override
    public List<DiseaseProfileRepository.DiseaseProfileSimple> getAll() {
        return diseaseProfileRepository.findAllProfiles();
    }

    @Override
    public Page<DiseaseProfileRepository.DiseaseProfileSimple> getAll(Pageable pageable) {
        return diseaseProfileRepository.findAllProfiles(pageable);
    }
}
