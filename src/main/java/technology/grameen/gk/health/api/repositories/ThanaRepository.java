package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Thana;

import java.util.List;

@Repository
public interface ThanaRepository extends JpaRepository<Thana, Long> {

    @Query("SELECT t FROM Thana t WHERE t.districtId = :districtId " +
            "ORDER BY t.thanaName ASC")
    List<Thana> findByDistrictId(@Param("districtId") Long districtId);
}
