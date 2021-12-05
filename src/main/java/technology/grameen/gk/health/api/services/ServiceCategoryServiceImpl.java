package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.ServiceCategory;
import technology.grameen.gk.health.api.repositories.lookup.ServiceCategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    ServiceCategoryRepository serviceCategoryRepository;

    public ServiceCategoryServiceImpl(ServiceCategoryRepository repo){
        this.serviceCategoryRepository = repo;
    }

    @Override
    @Transactional
    public ServiceCategory addCategory(ServiceCategory category) {
        category.setAlias(category.getName().toLowerCase());
        serviceCategoryRepository.save(category);
        return category;
    }

    @Override
    public List<ServiceCategory> getCategories() {
        return serviceCategoryRepository.findAll();
    }

    @Override
    public Page<ServiceCategory> getCategories(String name, Pageable pageable) {
        if(name.isEmpty()){
            return serviceCategoryRepository.findAll(pageable);
        }
        return serviceCategoryRepository.findAllByNameContainingIgnoreCase(name,pageable);
    }

    @Override
    public Optional<ServiceCategory> findById(Long id) {
        return serviceCategoryRepository.findById(id);
    }


    @Override
    public Optional<ServiceCategoryRepository.IServiceCategory> findByAlias(String alias) {
        return serviceCategoryRepository.findByAliasIgnoreCase(alias);
    }
}
