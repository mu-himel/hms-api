package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.CardRegistration;

import java.util.List;

@Repository
public interface CardRegistrationRepository extends JpaRepository<CardRegistration,Long> {

    List<CardRegistration> findByCardNumberContaining(String number);
}
