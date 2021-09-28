package technology.grameen.gk.health.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technology.grameen.gk.health.api.entity.HaPatientVisitLog;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.HaPatientVisitLogService;

@RestController
@RequestMapping("/api/v1/ha-patient-visit")
public class HaPatientVisitController {

    private HaPatientVisitLogService patientVisitLogService;

    public HaPatientVisitController(HaPatientVisitLogService patientVisitLogService) {
        this.patientVisitLogService = patientVisitLogService;
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> addPatientVisit(@RequestBody HaPatientVisitLog patientVisitLog){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                patientVisitLogService.addPatientVisit(patientVisitLog)
        ), HttpStatus.OK);
    }
}
