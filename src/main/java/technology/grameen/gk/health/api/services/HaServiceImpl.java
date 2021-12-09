package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.HaVillage;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.entity.Village;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.repositories.healthassistant.HaVillageRepository;

import java.util.List;

@Service
public class HaServiceImpl implements HaService{

    HaVillageRepository haVillageRepository;

    public HaServiceImpl(HaVillageRepository haVillageRepository) {
        this.haVillageRepository = haVillageRepository;
    }

    @Override
    @Transactional
    public HaVillage mapHaVillage(HaVillage haVillage) {
        return haVillageRepository.save(haVillage);
    }

    @Override
    public List<HaVillage> getHaVillageBy(String centerId, String villageId) {
        HealthCenter center = new HealthCenter();
        center.setId(Long.valueOf(centerId));

        Village village = new Village();
        village.setLgVillageId(Long.valueOf(villageId));
        return haVillageRepository.findByCenterAndVillage(center,village);
    }

    @Override
    public Page<?> getPatientsByHealthAssistant(Long employeeId, Pageable pageable) throws CustomException {
        if(employeeId == null){
            throw new CustomException("Please Specify Employee Id");
        }
        return haVillageRepository.findPatientsByEmployeeId(employeeId,pageable);
    }
}
