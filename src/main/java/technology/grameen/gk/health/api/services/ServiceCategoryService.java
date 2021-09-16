package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.ServiceCategory;
import technology.grameen.gk.health.api.repositories.lookup.ServiceCategoryRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceCategoryService {

     ServiceCategory addCategory(ServiceCategory category);

     List<ServiceCategory> getCategories();
     Page<ServiceCategory> getCategories(String name, Pageable pageable);

     Optional<ServiceCategory> findById(Long id);

     Optional<ServiceCategoryRepository.IServiceCategory> findByAlias(String alias);
}
