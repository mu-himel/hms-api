package technology.grameen.gk.health.api.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ha_patient_services")
public class HaPatientService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private HealthCenter center;

    @ManyToOne(fetch = FetchType.LAZY)
    private HaHome home;

    @ManyToOne
    private Employee healthAssistant;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Service service;

    @Column(length = 500)
    private String result;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getHealthAssistant() {
        return healthAssistant;
    }

    public void setHealthAssistant(Employee healthAssistant) {
        this.healthAssistant = healthAssistant;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public HaHome getHome() {
        return home;
    }

    public void setHome(HaHome home) {
        this.home = home;
    }

    public HealthCenter getCenter() {
        return center;
    }

    public void setCenter(HealthCenter center) {
        this.center = center;
    }
}
