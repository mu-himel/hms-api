package technology.grameen.gk.health.api.repositories.lookup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Page<Medicine> findAll(Pageable pageable);

    Page<Medicine> findAllByNameContainingIgnoreCase(String medicineName, Pageable pageable);
}
