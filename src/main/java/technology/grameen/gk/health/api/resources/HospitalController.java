package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.Hospital;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.hospital.HospitalService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/hospitals")
public class HospitalController {

    private static final Integer PAGE_SIZE = 10;
    private HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/list")
    public ResponseEntity<IResponse> getHospitals(){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                hospitalService.getHospitals()
        ), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<IResponse> list(
            @RequestParam Optional<String> thirdLevel,
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> code,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<Boolean> sortDesc){

        String _sortBy = sortBy.orElse(null);

        Sort sort = null;

        if(!_sortBy.isEmpty()) {
            sort =   (sortDesc.orElse(false)) ? Sort.by(_sortBy).descending()
                    : Sort.by(_sortBy).ascending();
        }
        Pageable pageable = (sort!=null)? PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE),sort)
                : PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE));

        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),
                hospitalService.getHospitals(name.orElse(""),
                        pageable)),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> addHospital(@RequestBody Hospital hospital){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                hospitalService.addHospital(hospital)
        ),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IResponse> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                hospitalService.getById(id)
        ), HttpStatus.OK);
    }
}
