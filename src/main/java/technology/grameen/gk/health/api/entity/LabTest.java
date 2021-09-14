package technology.grameen.gk.health.api.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "lab_tests")
public class LabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patient_invoice_id", referencedColumnName = "id")
    private PatientInvoice patientInvoice;

    @ManyToOne
    @JoinColumn(name = "specimen_id", referencedColumnName = "id")
    private Specimen specimen;

    @ManyToOne
    @JoinColumn(name = "lab_test_group_id", referencedColumnName = "id")
    private LabTestGroup labTestGroup;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @OneToMany(mappedBy = "labTest")
    private Set<LabTestDetail> details;

    private LocalDateTime testDate;
    private LocalDateTime receivedDate;
    private LocalDateTime printDate;
    private LocalDate deliveryDate;

    private String status;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Employee createdBy;

    @ManyToOne
    private Employee checkBy;

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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public PatientInvoice getPatientInvoice() {
        return patientInvoice;
    }

    public void setPatientInvoice(PatientInvoice patientInvoice) {
        this.patientInvoice = patientInvoice;
    }

    public Specimen getSpecimen() {
        return specimen;
    }

    public void setSpecimen(Specimen specimen) {
        this.specimen = specimen;
    }

    public LabTestGroup getLabTestGroup() {
        return labTestGroup;
    }

    public void setLabTestGroup(LabTestGroup labTestGroup) {
        this.labTestGroup = labTestGroup;
    }

    public Set<LabTestDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<LabTestDetail> details) {
        this.details = details;
    }

    public LocalDateTime getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDateTime testDate) {
        this.testDate = testDate;
    }

    public LocalDateTime getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public LocalDateTime getPrintDate() {
        return printDate;
    }

    public void setPrintDate(LocalDateTime printDate) {
        this.printDate = printDate;
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

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(Employee checkBy) {
        this.checkBy = checkBy;
    }
}
