package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import technology.grameen.gk.health.api.entity.MonthlyStatisticalCenterWiseView;
import technology.grameen.gk.health.api.projection.MonthlyStatisticalReport;

import java.util.List;

public interface ReportRepository extends JpaRepository<MonthlyStatisticalCenterWiseView,Long> {

    @Query(value = "SELECT * FROM MONTHLY_STATISTICAL_CENTER_WISE_VIEW mscwv WHERE thirdLevel = :regionCode",nativeQuery = true)
    List<MonthlyStatisticalReport> getMonthlyStatisticalReport(@Param("regionCode") String regionCode);
}
