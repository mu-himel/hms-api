package technology.grameen.gk.health.api.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.entity.JobHistory;

import java.time.LocalDate;
import java.util.Set;

public interface EmployeeDetail {
    Long getId();
    String getFullName();

    interface HealthCenter{
        Long getId();
        String getName();
        String getCenterCode();
        Integer getOfficeLevel();
        Integer getOfficeTypeId();
        String getFirstLevel();
        String getSecondLevel();
        String getThirdLevel();
        String getFourthLevel();
    }
    HealthCenter getCenter();

    interface JobHistory{
        String getDesignation();
        Boolean getLatest();
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate getJoinDate();
    }
    Set<JobHistory> getJobHistories();
    String getDesignation();
}
