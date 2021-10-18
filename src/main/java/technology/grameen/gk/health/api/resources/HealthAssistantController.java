package technology.grameen.gk.health.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technology.grameen.gk.health.api.entity.HaVillage;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.HaService;

@RestController
@RequestMapping("/api/v1/ha")
public class HealthAssistantController {

    HaService haService;

    public HealthAssistantController(HaService haService) {
        this.haService = haService;
    }

    @PostMapping("/map")
    public ResponseEntity<IResponse> mapHaToVillage(@RequestBody HaVillage haVillage){
        return new ResponseEntity<>(new EntityResponse<>(
            HttpStatus.OK.value(),
            haService.mapHaVillage(haVillage)
        ), HttpStatus.OK);

    }




}
