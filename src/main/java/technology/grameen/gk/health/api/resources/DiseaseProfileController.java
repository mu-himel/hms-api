package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.DiseaseProfile;
import technology.grameen.gk.health.api.entity.DiseaseType;
import technology.grameen.gk.health.api.entity.EventCategory;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.disease.DiseaseProfileService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/disease-profiles")
public class DiseaseProfileController {

    private static final Integer PAGE_SIZE = 10;
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

    @GetMapping("/list")
    public ResponseEntity<IResponse> getAll(@RequestParam Optional<Integer> page,
                                            @RequestParam Optional<Integer> size,
                                            @RequestParam Optional<String> sortBy,
                                            @RequestParam Optional<Boolean> sortDesc){
        
        String _sortBy = sortBy.orElse(null);
        _sortBy = (_sortBy.contains("active")) ? "isActive":_sortBy;
        Sort sort = null;

        if(!_sortBy.isEmpty()) {
            sort =   (sortDesc.orElse(false)) ? Sort.by(_sortBy).descending()
                    : Sort.by(_sortBy).ascending();
        }
        Pageable pageable = (sort!=null)? PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE),sort)
                : PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE));

        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                diseaseProfileService.getAll(pageable)
        ), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> addDiseaseProfile(@RequestBody DiseaseProfile diseaseProfile){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                diseaseProfileService.addDiseaseProfile(diseaseProfile)
        ), HttpStatus.OK);
    }

    @GetMapping("/by-type/{id}")
    public ResponseEntity<IResponse> getAll(@PathVariable("id") Long id){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                diseaseProfileService.getAll(new DiseaseType(id))
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IResponse> getDetail(@PathVariable("id") Long id){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                diseaseProfileService.getDetail(id)
        ), HttpStatus.OK);
    }
}
