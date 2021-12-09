package technology.grameen.gk.health.api.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Table(name = "business_targets")
public class BusinessTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String yearMonth;

    @ManyToOne(fetch = FetchType.LAZY)
    private HealthCenter createdOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    private HealthCenter forOffice;

    @PositiveOrZero
    private Integer hcPatientPerDay;

    @PositiveOrZero
    private Integer satPatientPerDay;

    @PositiveOrZero
    private Double hcIncomePerDay;

    @PositiveOrZero
    private Double satIncomePerDay;

    @ManyToOne
    private Employee createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public HealthCenter getCreatedOffice() {
        return createdOffice;
    }

    public void setCreatedOffice(HealthCenter createdOffice) {
        this.createdOffice = createdOffice;
    }

    public HealthCenter getForOffice() {
        return forOffice;
    }

    public void setForOffice(HealthCenter forOffice) {
        this.forOffice = forOffice;
    }

    public Integer getHcPatientPerDay() {
        return hcPatientPerDay;
    }

    public void setHcPatientPerDay(Integer hcPatientPerDay) {
        this.hcPatientPerDay = hcPatientPerDay;
    }

    public Integer getSatPatientPerDay() {
        return satPatientPerDay;
    }

    public void setSatPatientPerDay(Integer satPatientPerDay) {
        this.satPatientPerDay = satPatientPerDay;
    }

    public Double getHcIncomePerDay() {
        return hcIncomePerDay;
    }

    public void setHcIncomePerDay(Double hcIncomePerDay) {
        this.hcIncomePerDay = hcIncomePerDay;
    }

    public Double getSatIncomePerDay() {
        return satIncomePerDay;
    }

    public void setSatIncomePerDay(Double satIncomePerDay) {
        this.satIncomePerDay = satIncomePerDay;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
