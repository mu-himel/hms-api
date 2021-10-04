package technology.grameen.gk.health.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.CardRegistration;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.card_registration.CardRegistrationService;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    CardRegistrationService cardRegistrationService;

    public RegistrationController(CardRegistrationService cardRegistrationService) {
        this.cardRegistrationService = cardRegistrationService;
    }

    @PostMapping("/add-member")
    public ResponseEntity<IResponse> addMember(@RequestBody CardRegistration cardRegistration){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                cardRegistrationService.addCardMembers(cardRegistration)
        ), HttpStatus.OK);
    }
}
