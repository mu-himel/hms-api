package technology.grameen.gk.health.api.services;

import technology.grameen.gk.health.api.entity.HaVillage;

import java.util.List;

public interface HaService {

    HaVillage mapHaVillage(HaVillage haVillage);

    List<HaVillage> getHaVillageBy(String centerId, String villageId);
}
