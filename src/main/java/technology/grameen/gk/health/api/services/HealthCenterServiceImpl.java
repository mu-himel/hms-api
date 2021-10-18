package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.repositories.lookup.HealthCenterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HealthCenterServiceImpl implements HealthCenterService {


    HealthCenterRepository healthCenterRepository;

    public HealthCenterServiceImpl(HealthCenterRepository repo){
        this.healthCenterRepository = repo;
    }

    @Override
    @Transactional
    public HealthCenter addCenter(HealthCenter center) {
        healthCenterRepository.save(center);
        return center;
    }

    @Override
    public HealthCenter updateCenter(HealthCenter req) {
        HealthCenter center = healthCenterRepository.findById(req.getId()).get();
        if(center==null){
            throw new RuntimeException("Center not found");
        }
        center.setCenterCode(req.getCenterCode());
        center.setName(req.getName());
        center.setFirstLevel(req.getFirstLevel());
        center.setSecondLevel(req.getSecondLevel());
        center.setThirdLevel(req.getThirdLevel());
        center.setFourthLevel(req.getFourthLevel());
        center.setFifthLevel(req.getFifthLevel());
        healthCenterRepository.save(center);
        return center;
    }

    @Override
    public Page<HealthCenter> getCenters(Pageable pageable) {
        return healthCenterRepository.findAll(pageable);
    }

    @Override
    public Page<HealthCenter> getCenters(String thirdLevel,
                                         String name,
                                         String code,
                                         Pageable pageable) {

        if(thirdLevel.isEmpty() && name.isEmpty() && code.isEmpty()){
            return healthCenterRepository.findAll(pageable);
        }
        if(!name.isEmpty() && thirdLevel.isEmpty() && code.isEmpty()){
            return healthCenterRepository.findAllByNameContainingIgnoreCase(name,pageable);
        }
        if(name.isEmpty() && !thirdLevel.isEmpty() && code.isEmpty()){
            return healthCenterRepository.findAllByThirdLevel(thirdLevel,pageable);
        }
        if(name.isEmpty() && thirdLevel.isEmpty() && !code.isEmpty()){
            return healthCenterRepository.findAllByCenterCode(code,pageable);
        }
        return healthCenterRepository.findAllByNameContainingIgnoreCaseAndCenterCodeAndThirdLevel(name,code,thirdLevel,pageable);
    }

    @Override
    public List<HealthCenter> getCenters() {
        return healthCenterRepository.findAll();
    }

    @Override
    public List<HealthCenter> getRaCenters() {
        return healthCenterRepository.findByOfficeTypeId(5);
    }

    @Override
    public Optional<HealthCenter> findById(Long id) {
        return healthCenterRepository.findById(id);
    }

    @Override
    public Optional<HealthCenter> getCenterByApiCenterId(Long apiCenterId) {
        return healthCenterRepository.findByApiOfficeId(apiCenterId);
    }

    @Override
    public List<HealthCenter> getCentersByKeyword(String keyword) {
        return healthCenterRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<HealthCenter> getCentersByThirdLevel(String thirdLevel) {
        return healthCenterRepository.findByThirdLevel(thirdLevel);
    }

    @Override
    public List<HealthCenter> getCentersByOfficeTypeId(Integer officeTypeId) {
        return healthCenterRepository.findByOfficeTypeId(officeTypeId);
    }

    @Override
    public List<HealthCenter> getCentersByOfficeLevel(Integer officeLevel) {
        return healthCenterRepository.findByOfficeLevel(officeLevel);
    }

    @Override
    public List<HealthCenter> getCenterById(Long id) {
        return healthCenterRepository.findCenterById(id);
    }

    @Override
    public List<String> getCenterIds() {
        return healthCenterRepository.getAllIds();
    }

    @Override
    public List<String> getCenterIds(String thirdLevelCode) {
        return healthCenterRepository.getAllIds(thirdLevelCode);
    }

    @Override
    public List<Long> getCenterIdByThirdLevel(String thirdLevelCode) {
        return healthCenterRepository.getAllIdByThirdLevel(thirdLevelCode);
    }

    @Override
    public Optional<HealthCenter> getCenterByCenterCode(String code) {
        return healthCenterRepository.findByCenterCode(code);
    }
}
