package technology.grameen.gk.health.api.repositories.lookup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Specimen;

@Repository
public interface SpecimenRepository extends JpaRepository<Specimen,Integer> {
}
