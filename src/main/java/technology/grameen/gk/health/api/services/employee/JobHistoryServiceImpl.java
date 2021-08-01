package technology.grameen.gk.health.api.services.employee;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.JobHistory;
import technology.grameen.gk.health.api.repositories.JobHistoryRepository;

@Service
public class JobHistoryServiceImpl implements JobHistoryService {

    private JobHistoryRepository jobHistoryRepository;

    public JobHistoryServiceImpl(JobHistoryRepository jobHistoryRepository) {
        this.jobHistoryRepository = jobHistoryRepository;
    }

    @Override
    @Transactional
    public JobHistory addJobHistory(JobHistory jobHistory) {
        this.updateOldJobHistory(jobHistory.getEmployee());
        jobHistory.setLatest(true);
        return jobHistoryRepository.save(jobHistory);
    }

    @Override
    @Transactional
    public void updateOldJobHistory(Employee employee) {
        jobHistoryRepository.updateAllToOldByEmployee(employee,false);
    }
}
