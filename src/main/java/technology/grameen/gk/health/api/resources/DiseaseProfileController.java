package technology.grameen.gk.health.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technology.grameen.gk.health.api.entity.DiseaseType;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.disease.DiseaseProfileService;

@RestController
@RequestMapping("/api/v1/disease-profiles")
public class DiseaseProfileController {

    private DiseaseProfileService diseaseProfileService;

    public DiseaseProfileController(DiseaseProfileService diseaseProfileService) {
        this.diseaseProfileService = diseaseProfileService;
    }

    @GetMapping("")
    public ResponseEntity<IResponse> getAll(){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                diseaseProfileService.getAll()
        ), HttpStatus.OK);
    }

    @GetMapping("/by-type/{id}")
    public ResponseEntity<IResponse> getAll(@PathVariable("id") Long id){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                diseaseProfileService.getAll(new DiseaseType(id))
        ), HttpStatus.OK);
    }
}
