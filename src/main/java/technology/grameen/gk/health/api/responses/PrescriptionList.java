package technology.grameen.gk.health.api.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PrescriptionList {

    private Long id;
    private Long prescriptionId;
    private String pNumber;
    private LocalDateTime lastFreeVisitDate;
    private LocalDateTime createdAt;
    private String fullName;
    private String centerCode;

    public PrescriptionList(Long id, Long prescriptionId, String pNumber, LocalDateTime lastFreeVisitDate,
                            LocalDateTime createdAt, String fullName, String centerCode) {
        this.id = id;
        this.prescriptionId = prescriptionId;
        this.pNumber = pNumber;
        this.lastFreeVisitDate = lastFreeVisitDate;
        this.createdAt = createdAt;
        this.fullName = fullName;
        this.centerCode = centerCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDateTime getLastFreeVisitDate() {
        return lastFreeVisitDate;
    }

    public void setLastFreeVisitDate(LocalDateTime lastFreeVisitDate) {
        this.lastFreeVisitDate = lastFreeVisitDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCenterCode() {
        return centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }
}
