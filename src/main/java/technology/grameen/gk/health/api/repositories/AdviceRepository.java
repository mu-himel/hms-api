package technology.grameen.gk.health.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Advice;

import java.util.List;

@Repository
public interface AdviceRepository extends JpaRepository<Advice,Long> {

    @Query(value = "SELECT a FROM Advice a WHERE a.title LIKE '%' || :title || '%'")
    List<Advice> findAllByTitle(@Param("title") String title);

    @Query(value = "SELECT a FROM Advice a WHERE a.title LIKE '%' || :title || '%'",
    countQuery = "SELECT count(*) FROM Advice a WHERE a.title LIKE '%' || :title || '%'")
    Page<Advice> findAllByTitle(String title, Pageable pageable);
}
