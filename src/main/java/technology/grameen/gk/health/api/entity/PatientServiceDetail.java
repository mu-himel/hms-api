package technology.grameen.gk.health.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_service_details")
public class PatientServiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id",referencedColumnName = "serviceId")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "patient_invoice_id",referencedColumnName = "id")
    private PatientInvoice patientInvoice;


    private Integer roomNumber;

    private String referredDoctor;

    private Integer serviceQty;

    private BigDecimal serviceAmount;

    private BigDecimal discountAmount;

    private BigDecimal payableAmount;

    private Boolean isReportGenerated;

    private Boolean refunded = false;

    @ManyToOne
    private Employee healthAssistant;

    @ManyToOne
    private Employee treatmentBy;

    @ManyToOne
    private DiseaseType diseaseType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @JsonBackReference
    @JsonIgnore
    public PatientInvoice getPatientInvoice() {
        return patientInvoice;
    }


    public void setPatientInvoice(PatientInvoice patientInvoice) {
        this.patientInvoice = patientInvoice;
    }

    public Integer getServiceQty() {
        return serviceQty;
    }

    public void setServiceQty(Integer serviceQty) {
        this.serviceQty = serviceQty;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public BigDecimal getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(BigDecimal serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Boolean getReportGenerated() {
        return isReportGenerated;
    }

    public void setReportGenerated(Boolean reportGenerated) {
        isReportGenerated = reportGenerated;
    }

    public DiseaseType getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(DiseaseType diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getReferredDoctor() {
        return referredDoctor;
    }

    public void setReferredDoctor(String referredDoctor) {
        this.referredDoctor = referredDoctor;
    }

    public Employee getTreatmentBy() {
        return treatmentBy;
    }

    public void setTreatmentBy(Employee treatmentBy) {
        this.treatmentBy = treatmentBy;
    }

    public Boolean getRefunded() {
        return refunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    public Employee getHealthAssistant() {
        return healthAssistant;
    }

    public void setHealthAssistant(Employee healthAssistant) {
        this.healthAssistant = healthAssistant;
    }
}
