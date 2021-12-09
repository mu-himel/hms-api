package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.HaVillage;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.HaService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ha")
public class HealthAssistantController {

    private static final Integer PAGE_SIZE = 10;
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

    @GetMapping("")
    public ResponseEntity<IResponse> getHaList(@RequestParam String centerId,
                                               @RequestParam String villageId){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                haService.getHaVillageBy(centerId,villageId)
        ), HttpStatus.OK);
    }

    @GetMapping("/patients")
    public ResponseEntity<IResponse> getPatients(@RequestParam Optional<Long> employeeId,
                                                 @RequestParam Optional<Integer> page,
                                                 @RequestParam Optional<Integer> size,
                                                 @RequestParam Optional<String> sortBy,
                                                 @RequestParam Optional<Boolean> sortDesc) throws CustomException{

        String _sortBy = sortBy.orElse(null);

        Sort sort = null;

        if(!_sortBy.isEmpty()) {
            sort =   (sortDesc.orElse(false)) ? Sort.by(_sortBy).descending()
                    : Sort.by(_sortBy).ascending();
        }

        Pageable pageable = (sort!=null)? PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE),sort) :
                PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE));

        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                haService.getPatientsByHealthAssistant(employeeId.orElse(null),pageable)
        ), HttpStatus.OK);
    }




}
