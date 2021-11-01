package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.HaPatientVisitLog;
import technology.grameen.gk.health.api.repositories.HaPatientVisitLogRepository;

import java.util.Optional;

@Service
public class HaPatientVisitLogServiceImpl implements HaPatientVisitLogService{

    private HaPatientVisitLogRepository patientVisitLogRepository;

    public HaPatientVisitLogServiceImpl(HaPatientVisitLogRepository patientVisitLogRepository) {
        this.patientVisitLogRepository = patientVisitLogRepository;
    }

    @Override
    @Transactional
    public HaPatientVisitLog addPatientVisit(HaPatientVisitLog patientVisitLog) {
        return patientVisitLogRepository.save(patientVisitLog);
    }

    @Override
    public Page<HaPatientVisitLogRepository.PageVisitLog> getVisitLogs(Pageable pageable) {
        return patientVisitLogRepository.getAll(pageable);
    }

    @Override
    public Optional<?> getPatientVisitLogByPatientAndDate(Long centerId,Long pid, String dt) {

        return patientVisitLogRepository.findVisitLogByPatientAndDate(centerId,pid,dt);
    }
}
