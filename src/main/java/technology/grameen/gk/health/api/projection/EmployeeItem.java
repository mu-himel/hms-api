package technology.grameen.gk.health.api.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import technology.grameen.gk.health.api.entity.HealthCenter;

import java.time.LocalDateTime;

public interface EmployeeItem {
    Long getId();
    Long getApiEmployeeId();
    String getFullName();
    String getDesignation();
    Integer getDesignationId();
    String getContactNumber();
    String getEmail();
    String getRole();
    interface HealthCenter{
        Long getId();
        String getName();
        String getCenterCode();
        Integer getOfficeTypeId();
        Integer getOfficeLevel();
    }
    Boolean getIsActive();
    HealthCenter getCenter();
    String getEmployeeCode();

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime getCreatedAt();

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime getLastUpdatedAt();
}
