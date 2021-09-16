package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.projection.ServiceListItem;
import technology.grameen.gk.health.api.entity.LabTestGroup;
import technology.grameen.gk.health.api.entity.LabTestUnit;
import technology.grameen.gk.health.api.entity.Service;
import technology.grameen.gk.health.api.entity.ServiceCategory;
import technology.grameen.gk.health.api.repositories.LabTestAttributeRepository;
import technology.grameen.gk.health.api.repositories.lookup.ServiceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class HealthServiceImpl implements HealthServiceInterface {

    ServiceRepository serviceRepository;
    LabTestUnitService labTestUnitService;
    LabTestAttributeRepository labTestAttributeRepository;

    public HealthServiceImpl(ServiceRepository serviceRepository,
                             LabTestUnitService labTestUnitService,
                             LabTestAttributeRepository labTestAttributeRepository){
        this.serviceRepository=serviceRepository;
        this.labTestUnitService=labTestUnitService;
        this.labTestAttributeRepository = labTestAttributeRepository;
    }

    @Override
    @Transactional
    public Service addService(Service service) {
        ServiceCategory serviceCategory = service.getServiceCategory();
        LabTestGroup labTestGroup = service.getLabTestGroup();
        if(serviceCategory != null) {
            serviceCategory.addServices(service);

        }
        if(labTestGroup != null){
            labTestGroup.addService(service);
        }
        serviceRepository.save(service);
        serviceRepository.flush();
        return service;
    }

    public Optional<Service> findServiceById(Long id){
        Optional<Service> serviceRes = serviceRepository.findById(id);
        Service service = null;
        if(serviceRes.isPresent()){
            service = serviceRes.get();

        }
        return Optional.ofNullable(service);
    }

    @Override
    public List<ServiceListItem> getAll() {
        return serviceRepository.findAllServices();
    }

    @Override
    public Page<ServiceListItem> getAll(String serviceName, Pageable pageable) {
        if(serviceName.isEmpty()){
            return serviceRepository.findAllServices(pageable);
        }
        return serviceRepository.findAllServices(serviceName,pageable);
    }

    @Override
    public List<ServiceListItem> getLabServices() {

        return serviceRepository.findByIsLabTestEquals(true);
    }

    @Override
    @Transactional
    public Service addServiceAttributes(Service s) throws Exception {
        Optional<Service> serviceOptional = findServiceById(s.getServiceId());
        if(!serviceOptional.isPresent()){
            throw new Exception("Service Not Found");
        }

        labTestAttributeRepository.flush();
        Service service = serviceOptional.get();
        s.getLabTestAttributes().stream().map((attr)->{
            LabTestUnit u = attr.getLabTestUnit();
            if(u != null && u.getId()>0) {
                u.addLabTestAttribute(attr);
            }
            service.addLabTestAttribute(attr);
            return attr;
        }).collect(Collectors.toSet());

        labTestAttributeRepository.saveAll(service.getLabTestAttributes());
        serviceRepository.save(service);

        return service;

    }

    @Override
    public void deleteAttributeById(Long id) {
        labTestAttributeRepository.deleteById(id);
    }

    @Override
    public List<ServiceRepository.IServiceList> findByServiceCategory(ServiceCategory serviceCategory) {
        return serviceRepository.findByServiceCategory(serviceCategory);
    }
}
