package technology.grameen.gk.health.api.repositories.lookup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.MedicineType;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineTypeRepository extends JpaRepository<MedicineType, Long> {

    List<MedicineType> findByNameContainingIgnoreCase(String name);

    Optional<MedicineType> findByName(String name);
}
