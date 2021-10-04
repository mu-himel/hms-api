package technology.grameen.gk.health.api.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface PrescriptionListItem {

    Long getId();
    Long getPrescriptionId();
    String getpNumber();

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime getLastFreeVisitDate();

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime getCreatedAt();
    String getFullName();
}
