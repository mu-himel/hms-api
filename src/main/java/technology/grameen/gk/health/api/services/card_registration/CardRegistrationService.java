package technology.grameen.gk.health.api.services.card_registration;

import technology.grameen.gk.health.api.entity.CardRegistration;
import technology.grameen.gk.health.api.entity.Patient;

import java.util.List;

public interface CardRegistrationService {

    Boolean register(Patient patient) throws Exception;

    Boolean register(Patient patient,Boolean existingPatient) throws Exception;

    CardRegistration getNewCardRegistrationRequest(Patient patient);

    Boolean addCardMembers(CardRegistration cardRegistration);

    List<CardRegistration> getCardRegistrationsByNumber(String number);
}
