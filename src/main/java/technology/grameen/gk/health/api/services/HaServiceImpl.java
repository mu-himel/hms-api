package technology.grameen.gk.health.api.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.HaVillage;
import technology.grameen.gk.health.api.repositories.HaVillageRepository;

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
}
