package technology.grameen.gk.health.api.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Set;

public interface EmployeeDetailInfo {
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

    interface Employee{
        Long getId();
    }

    interface EmployeeDetail {
        Long getId();
        String getBmdcRegNumber();
        String getAvailableDay();
        String getSpeciality();
        String getPrescriptionDegreeInst();
        Employee getEmployee();
    }

    EmployeeDetail getEmployeeDetail();
    Set<JobHistory> getJobHistories();
    String getDesignation();
}
