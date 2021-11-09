package technology.grameen.gk.health.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.requests.ExistingPatientRequest;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.patient.PatientManageService;
import technology.grameen.gk.health.api.services.card_registration.CardRegistrationService;

@RestController
@RequestMapping("/api/v1/existing-patient")
public class ExistingPatientController {

    PatientManageService patientManageService;
    CardRegistrationService cardRegistrationService;

    public ExistingPatientController(PatientManageService patientManageService,CardRegistrationService cardRegistrationService) {
        this.patientManageService = patientManageService;
        this.cardRegistrationService = cardRegistrationService;
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> addExistingPatient(@RequestBody ExistingPatientRequest patientRequest) throws Exception {
        Patient p = patientManageService.addPatient(patientRequest);
        p.addRegistration(patientRequest.getRegistration());
        cardRegistrationService.register(p,true);
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                p
        ), HttpStatus.OK);
    }
}
