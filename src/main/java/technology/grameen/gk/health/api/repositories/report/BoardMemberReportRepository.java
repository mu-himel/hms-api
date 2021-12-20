package technology.grameen.gk.health.api.repositories.report;


import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardMemberReportRepository extends ReportRepository {

//    @Query(value = "select count(r) from HealthCenter r WHERE r.officeTypeId=5")
//    Optional<Integer> totalRegion();
//
//    @Query(value = "select count(c) from HealthCenter c WHERE r.officeTypeId=6")
//    Optional<Integer> totalCenter();
}
