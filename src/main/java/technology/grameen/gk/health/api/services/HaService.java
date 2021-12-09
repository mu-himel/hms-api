package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.HaVillage;
import technology.grameen.gk.health.api.exceptions.CustomException;

import java.util.List;

public interface HaService {

    HaVillage mapHaVillage(HaVillage haVillage);

    List<HaVillage> getHaVillageBy(String centerId, String villageId);

    Page<?> getPatientsByHealthAssistant(Long employeeId, Pageable pageable) throws CustomException;
}
