package technology.grameen.gk.health.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.requests.HaPatientServiceSell;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.HaService;
import technology.grameen.gk.health.api.services.IHaPatientService;

@RestController
@RequestMapping("/api/v1/ha-patient-service")
public class HaPatientServiceController {

    IHaPatientService haPatientService;

    public HaPatientServiceController(IHaPatientService haPatientService) {
        this.haPatientService = haPatientService;
    }

    @PostMapping("/sell")
    public ResponseEntity<IResponse> sellService(@RequestBody HaPatientServiceSell haPatientServiceSell)
                                                                    throws Exception {

            return new ResponseEntity<>(new EntityResponse<>(
                    HttpStatus.OK.value(),
                    haPatientService.addHaPatientService(haPatientServiceSell)
            ), HttpStatus.OK);
    }

    @GetMapping("/patient/{id}/{date}")
    public ResponseEntity<IResponse> getServiceByPatientAndDate(@PathVariable("id") Long patientId,
                                                                @PathVariable("date") String date){


        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                haPatientService.getPatientServicesByDate(patientId,date)
        ), HttpStatus.OK);
    }



}
