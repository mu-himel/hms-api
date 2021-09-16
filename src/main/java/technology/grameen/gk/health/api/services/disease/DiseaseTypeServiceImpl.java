package technology.grameen.gk.health.api.services.disease;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.DiseaseType;
import technology.grameen.gk.health.api.repositories.lookup.DiseaseTypeRepository;

import java.util.List;

@Service
public class DiseaseTypeServiceImpl implements DiseaseTypeService{

    private DiseaseTypeRepository diseaseTypeRepository;

    public DiseaseTypeServiceImpl(DiseaseTypeRepository diseaseTypeRepository) {
        this.diseaseTypeRepository = diseaseTypeRepository;
    }

    @Override
    @Transactional
    public DiseaseType addDiseaseType(DiseaseType diseaseType) {
        return diseaseTypeRepository.save(diseaseType);
    }

    @Override
    public List<DiseaseType> getAll() {
        return diseaseTypeRepository.findAll();
    }
}
