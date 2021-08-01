package technology.grameen.gk.health.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.JobHistory;

@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory,Long> {

    @Modifying
    @Query("UPDATE JobHistory j SET j.latest=:latest where j.employee = :employee")
    void updateAllToOldByEmployee(@Param("employee") Employee employee,@Param("latest") Boolean status);
}
