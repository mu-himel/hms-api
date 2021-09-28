package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.HaPatientVisitLog;

@Repository
public interface HaPatientVisitLogRepository extends JpaRepository<HaPatientVisitLog,Long> {
}
