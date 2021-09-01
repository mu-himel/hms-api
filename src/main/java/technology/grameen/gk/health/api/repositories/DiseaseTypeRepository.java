package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.DiseaseType;

@Repository
public interface DiseaseTypeRepository extends JpaRepository<DiseaseType,Long> {
}
