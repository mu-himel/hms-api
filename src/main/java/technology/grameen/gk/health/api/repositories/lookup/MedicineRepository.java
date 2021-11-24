package technology.grameen.gk.health.api.repositories.lookup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Medicine;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    @Query(value = "select m from Medicine m JOIN FETCH m.medicineBrand mb" +
            " JOIN FETCH m.medicineGroup mg",
    countQuery = "select count(*) from Medicine m JOIN m.medicineBrand mb" +
    " JOIN m.medicineGroup mg")
    Page<Medicine> findAll(Pageable pageable);

    @Query(value = "select m from Medicine m JOIN FETCH m.medicineBrand mb" +
            " JOIN FETCH m.medicineGroup mg WHERE lower(m.name) LIKE CONCAT('%' ,  lower(:medicineName) , '%')",
            countQuery = "select count(*) from Medicine m JOIN m.medicineBrand mb" +
                    " JOIN m.medicineGroup mg WHERE lower(m.name) LIKE CONCAT('%' , lower(:medicineName) , '%')")
    Page<Medicine> findAllByNameContainingIgnoreCase(@Param("medicineName") String medicineName,
                                                     Pageable pageable);

    @Query(value = "select m from Medicine m JOIN FETCH m.medicineBrand mb" +
            " JOIN FETCH m.medicineGroup mg WHERE lower(m.name) LIKE CONCAT(lower(:medicineName) , '%')" +
            " ORDER BY m.name ASC",
            countQuery = "select count(*) from Medicine m JOIN m.medicineBrand mb" +
                    " JOIN m.medicineGroup mg WHERE lower(m.name) LIKE CONCAT(lower(:medicineName) , '%')")
    List<Medicine> findAllByNameContainingIgnoreCase(@Param("medicineName") String medicineName);
}
