package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.CardRegistration;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRegistrationRepository extends JpaRepository<CardRegistration,Long> {

    List<CardRegistration> findByCardNumberContaining(String number);

    Optional<CardRegistration> findByCardNumber(String cardNumber);
}
