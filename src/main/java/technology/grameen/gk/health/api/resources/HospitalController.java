package technology.grameen.gk.health.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.Hospital;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.hospital.HospitalService;

@RestController
@RequestMapping("/api/v1/hospitals")
public class HospitalController {

    private HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("")
    public ResponseEntity<IResponse> getHospitals(){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                hospitalService.getHospitals()
        ), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> addHospital(@RequestBody Hospital hospital){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                hospitalService.addHospital(hospital)
        ),HttpStatus.OK);
    }
}
