package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.HaPatientVisitLog;
import technology.grameen.gk.health.api.repositories.healthassistant.HaPatientVisitLogRepository;

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
    public Page<HaPatientVisitLogRepository.PageVisitLog> getVisitLogs(Long centerId, Pageable pageable) {
        return patientVisitLogRepository.getAll(centerId,pageable);
    }

    @Override
    public Page<HaPatientVisitLogRepository.PageVisitLog> getVisitLogs(String regionCode, Long centerId,
                                                                       Pageable pageable) {

        if(regionCode!=null && centerId==null){
            return patientVisitLogRepository.getAll(regionCode,pageable);
        }else if((regionCode==null || regionCode!=null) && centerId!=null){
            return patientVisitLogRepository.getAll(centerId,pageable);
        }

        return null;
    }

    @Override
    public Optional<?> getPatientVisitLogByPatientAndDate(Long centerId,Long pid, String dt) {

        return patientVisitLogRepository.findVisitLogByPatientAndDate(centerId,pid,dt);
    }
}
