package technology.grameen.gk.health.api.notification.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.notification.sms.entity.SmsSentResponse;

@Repository
public interface SmsSentResponseRepository extends JpaRepository<SmsSentResponse,Long> {
}
