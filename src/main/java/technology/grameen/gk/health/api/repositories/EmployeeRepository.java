package technology.grameen.gk.health.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.projection.EmployeeItem;
import technology.grameen.gk.health.api.responses.EmployeeDetail;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query(value = "SELECT e from Employee e JOIN FETCH e.center c",
    countQuery = "SELECT count(*) from Employee e")
    Page<EmployeeItem> findAllEmployee(Pageable pageable);

    Page<EmployeeItem> findAllByCenterId(Long centerId,Pageable pageable);
    Page<EmployeeItem> findAllByEmployeeCodeContaining(String employeeCode,Pageable pageable);
    Page<EmployeeItem> findAllByFullNameContainingIgnoreCase(String fullName,Pageable pageable);
    Page<EmployeeItem> findAllByContactNumberContaining(String contactNo,Pageable pageable);
    Page<EmployeeItem> findAllByEmailContainingIgnoreCase(String email,Pageable pageable);

    @Query("Select e from Employee e WHERE  e.apiEmployeeId=:id")
    Optional<Employee> findByApiEmployeeId(@Param("id") Long id);

    @Query("Select e from Employee e JOIN FETCH e.center c LEFT JOIN FETCH e.jobHistories jh WHERE  e.id=:id")
    Optional<EmployeeDetail> findByEmployeeId(@Param("id") Long id);

    @Query(value = "SELECT COUNT(e.id) FROM Employee e WHERE e.apiEmployeeId=:id")
    Integer getCount(@Param("id") Long id);

    List<EmployeeItem> findAllByDesignationContainingIgnoreCase(String designation);
}
