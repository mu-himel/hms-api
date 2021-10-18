package technology.grameen.gk.health.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.HaHome;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.HaHomeService;

@RestController
@RequestMapping("/api/v1/ha-home")
public class HaHomeController {

    HaHomeService haHomeService;

    public HaHomeController(HaHomeService haHomeService) {
        this.haHomeService = haHomeService;
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> addHaHome(@RequestBody HaHome haHome){
        return new ResponseEntity<>(new EntityResponse<>(
            HttpStatus.OK.value(),
                haHomeService.addHaHome(haHome)
        ), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<IResponse> getHomes(
                                        @RequestParam String villageId){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                haHomeService.getHomes(villageId)
        ), HttpStatus.OK);
    }
}
