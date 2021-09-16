package technology.grameen.gk.health.api.repositories.lookup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.MedicineGroup;

@Repository
public interface MedicineGroupRepository extends JpaRepository<MedicineGroup,Long> {

    Page<MedicineGroup> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
