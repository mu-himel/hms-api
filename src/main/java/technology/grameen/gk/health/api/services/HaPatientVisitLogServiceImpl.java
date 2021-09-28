package technology.grameen.gk.health.api.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.HaPatientVisitLog;
import technology.grameen.gk.health.api.repositories.HaPatientVisitLogRepository;

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
}
