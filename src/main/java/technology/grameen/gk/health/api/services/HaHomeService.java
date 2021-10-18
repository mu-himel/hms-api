package technology.grameen.gk.health.api.services;

import technology.grameen.gk.health.api.entity.HaHome;

import java.util.List;

public interface HaHomeService {

    HaHome addHaHome(HaHome haHome);

    List<HaHome> getHomes(String villageId);
}
