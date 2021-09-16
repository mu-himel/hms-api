package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.ServiceCategory;
import technology.grameen.gk.health.api.projection.ServiceListItem;
import technology.grameen.gk.health.api.entity.Service;
import technology.grameen.gk.health.api.repositories.lookup.ServiceRepository;

import java.util.List;
import java.util.Optional;

public interface HealthServiceInterface {

    public Service addService(Service service);

    public Optional<Service> findServiceById(Long id);

    public Service addServiceAttributes(Service service) throws Exception;

    List<ServiceListItem> getAll();
    Page<ServiceListItem> getAll(String serviceName, Pageable pageable);



    public List<ServiceListItem> getLabServices();

    void deleteAttributeById(Long id);

    List<ServiceRepository.IServiceList> findByServiceCategory(ServiceCategory serviceCategory);
}
