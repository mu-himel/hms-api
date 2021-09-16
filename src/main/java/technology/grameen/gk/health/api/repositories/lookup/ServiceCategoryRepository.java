package technology.grameen.gk.health.api.repositories.lookup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.ServiceCategory;

import java.util.Optional;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory,Long> {

    Page<ServiceCategory> findAllByNameContainingIgnoreCase(String name , Pageable page);

    interface IServiceCategory{
        Long getId();
        String getName();
        String getAlias();
    }
    Optional<IServiceCategory> findByAliasIgnoreCase(String alias);
}
