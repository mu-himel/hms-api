package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.HaPatientVisitLog;
import technology.grameen.gk.health.api.repositories.HaPatientVisitLogRepository;

public interface HaPatientVisitLogService {

    HaPatientVisitLog addPatientVisit(HaPatientVisitLog patientVisitLog);

    Page<HaPatientVisitLogRepository.PageVisitLog> getVisitLogs(Pageable pageable);
}
