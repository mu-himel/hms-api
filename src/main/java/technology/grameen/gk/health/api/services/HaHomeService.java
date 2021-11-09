package technology.grameen.gk.health.api.services;

import technology.grameen.gk.health.api.entity.HaHome;
import technology.grameen.gk.health.api.repositories.healthassistant.HaHomeRepository;

import java.util.List;

public interface HaHomeService {

    HaHome addHaHome(HaHome haHome);

    List<HaHomeRepository.HaHomeItem> getHomes(String villageId);
}
