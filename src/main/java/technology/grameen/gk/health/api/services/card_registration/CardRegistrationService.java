package technology.grameen.gk.health.api.services.card_registration;

import technology.grameen.gk.health.api.entity.CardRegistration;
import technology.grameen.gk.health.api.entity.Patient;

public interface CardRegistrationService {

    Boolean register(Patient patient) throws Exception;

    CardRegistration getNewCardRegistrationRequest(Patient patient);

    Boolean addCardMembers(CardRegistration cardRegistration);
}
