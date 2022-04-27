package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.District;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query(value = "SELECT d FROM District d WHERE d.divisionId = :divisionId " +
            "ORDER BY d.districtName ASC")
    List<District> findByDivisionId(@Param("divisionId") Long divisionId);
}
