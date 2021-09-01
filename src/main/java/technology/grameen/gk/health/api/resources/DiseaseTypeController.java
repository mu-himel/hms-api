package technology.grameen.gk.health.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technology.grameen.gk.health.api.entity.DiseaseType;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.disease.DiseaseTypeService;

@RequestMapping("/api/v1/disease-types")
@RestController
public class DiseaseTypeController {

    private DiseaseTypeService diseaseTypeService;

    public DiseaseTypeController(DiseaseTypeService diseaseTypeService) {
        this.diseaseTypeService = diseaseTypeService;
    }

    @GetMapping("")
    public ResponseEntity<IResponse> getDiseaseTypes(){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                diseaseTypeService.getAll()
        ),HttpStatus.OK);
    }
}
