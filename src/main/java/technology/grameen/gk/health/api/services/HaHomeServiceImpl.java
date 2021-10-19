package technology.grameen.gk.health.api.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.HaHome;
import technology.grameen.gk.health.api.entity.Village;
import technology.grameen.gk.health.api.repositories.HaHomeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HaHomeServiceImpl implements HaHomeService{

    HaHomeRepository haHomeRepository;

    public HaHomeServiceImpl(HaHomeRepository haHomeRepository) {
        this.haHomeRepository = haHomeRepository;
    }

    @Override
    @Transactional
    public HaHome addHaHome(HaHome haHome) {
        return haHomeRepository.save(haHome);
    }

    @Override
    public List<HaHomeRepository.HaHomeItem> getHomes(String villageId) {
        if(villageId!=""){
            Village village = new Village();
            village.setLgVillageId(Long.parseLong(villageId));
            return haHomeRepository.findByVillage(village);
        }
        return new ArrayList<>();
    }
}
