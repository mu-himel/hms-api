package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Union;

import java.util.List;

@Repository
public interface UnionRepository extends JpaRepository<Union, Long> {
    @Query(value = "SELECT u FROM Union u WHERE u.thanaId = : thanaId " +
            "ORDER BY u.unionName ASC")
    List<Union> findByThanaId(@Param("thanaId") Long thanaId);
}
