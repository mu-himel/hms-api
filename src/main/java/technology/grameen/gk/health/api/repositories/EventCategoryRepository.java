package technology.grameen.gk.health.api.repositories;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.EventCategory;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EventCategoryRepository extends JpaRepository<EventCategory,Integer> {
    Page<EventCategory> findAllByName(String name, Pageable pageable);

    interface EventCategoryDetail{
        Integer getId();
        String getName();
        String getDescription();
        Boolean getStatus();
        Boolean getSatellite();

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime getCreatedAt();
    }
    @Query(value = "SELECT ec FROM EventCategory ec WHERE ec.id=:id")
    Optional<EventCategoryDetail> findEventCategoryById(@Param("id") Integer id);
}
