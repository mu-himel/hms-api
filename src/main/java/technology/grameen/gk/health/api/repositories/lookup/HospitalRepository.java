package technology.grameen.gk.health.api.repositories.lookup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Long> {
}
