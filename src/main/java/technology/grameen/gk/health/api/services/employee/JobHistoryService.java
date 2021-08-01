package technology.grameen.gk.health.api.services.employee;

import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.JobHistory;

public interface JobHistoryService {

    JobHistory addJobHistory(JobHistory jobHistory);

    void updateOldJobHistory(Employee employee);
}
