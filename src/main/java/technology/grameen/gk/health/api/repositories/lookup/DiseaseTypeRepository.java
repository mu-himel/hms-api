package technology.grameen.gk.health.api.repositories.lookup;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.DiseaseType;

import java.util.List;

@Repository
public interface DiseaseTypeRepository extends JpaRepository<DiseaseType,Long> {
    List<DiseaseType> findByOrderByNameAsc();
}
