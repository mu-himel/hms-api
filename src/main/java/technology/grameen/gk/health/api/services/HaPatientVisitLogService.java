package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.HaPatientVisitLog;
import technology.grameen.gk.health.api.repositories.healthassistant.HaPatientVisitLogRepository;

import java.util.Optional;

public interface HaPatientVisitLogService {

    HaPatientVisitLog addPatientVisit(HaPatientVisitLog patientVisitLog);

    Page<HaPatientVisitLogRepository.PageVisitLog> getVisitLogs(Pageable pageable);

    Optional<?> getPatientVisitLogByPatientAndDate(Long centerId,Long pid, String dt);
}
