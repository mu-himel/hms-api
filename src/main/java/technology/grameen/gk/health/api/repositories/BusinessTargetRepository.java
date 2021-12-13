package technology.grameen.gk.health.api.repositories;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.BusinessTarget;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.HealthCenter;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BusinessTargetRepository extends JpaRepository<BusinessTarget,Long> {

    @Query(value = "SELECT bt FROM BusinessTarget bt " +
            "JOIN FETCH bt.createdOffice co " +
            "JOIN FETCH bt.forOffice fo " +
            "JOIN FETCH bt.createdBy cb " +
            "WHERE bt.createdOffice.id = :officeId AND bt.yearMonth = :yearMonth ")
    List<?> findByCreatedOfficeAndYearMonth(@Param("officeId") Long createdOffice_id,
                                            @Param("yearMonth") String yearMonth);

    interface BusinessTargetRow{

         Long getId();

         String getYearMonth();

         HealthCenter getCreatedOffice();

         HealthCenter getForOffice();

         Integer getHcPatientPerDay();

         Integer getSatPatientPerDay();

         Double getHcIncomePerDay();

         Double getSatIncomePerDay();

         Employee getCreatedBy();

        @JsonFormat(pattern = "yyyy-MM-dd")
         LocalDateTime getCreatedAt();

        @JsonFormat(pattern = "yyyy-MM-dd")
         LocalDateTime getUpdatedAt();

    }

    @Query(value = "select bt FROM BusinessTarget bt " +
            " JOIN FETCH bt.createdOffice " +
            " JOIN FETCH bt.forOffice",
    countQuery = "SELECT count(bt) FROM BusinessTarget bt JOIN bt.createdOffice Join bt.forOffice")
    Page<BusinessTargetRow> findAllBusinessTarget(Pageable pageable);
}
