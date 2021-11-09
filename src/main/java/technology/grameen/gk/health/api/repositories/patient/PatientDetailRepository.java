package technology.grameen.gk.health.api.repositories.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.PatientDetail;

@Repository
public interface PatientDetailRepository extends JpaRepository<PatientDetail,Long> {
}
