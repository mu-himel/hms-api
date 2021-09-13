package technology.grameen.gk.health.api.repositories.employee;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.EmployeeDetail;

@Repository
public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail,Long> {

}
