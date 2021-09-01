package technology.grameen.gk.health.api.services.disease;

import technology.grameen.gk.health.api.entity.DiseaseType;

import java.util.List;

public interface DiseaseTypeService {

    DiseaseType addDiseaseType(DiseaseType diseaseType);

    List<DiseaseType> getAll();
}
