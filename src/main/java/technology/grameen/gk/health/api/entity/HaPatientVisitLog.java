package technology.grameen.gk.health.api.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ha_patient_visit_logs")
public class HaPatientVisitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer bpDiastolic;
    private Integer bpSystolic;

    private String height;
    private String weight;
    private Integer noOfFamilyMember;

    @ManyToOne
    private DiseaseProfile diseaseProfile;

    private Boolean isTakingMedicine;
    private Boolean isDoingCounseling;

    private Boolean adviceToGoAtHealthCenter;
    private Boolean adviceToGoAtSatellite;
    private Boolean adviceToGoAtCamp;

    @ManyToOne(fetch = FetchType.LAZY)
    private HaHome home;

    @ManyToOne
    private HealthCenter center;

    @ManyToOne
    private Employee healthAssistant;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

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

    public Integer getBpDiastolic() {
        return bpDiastolic;
    }

    public void setBpDiastolic(Integer bpDiastolic) {
        this.bpDiastolic = bpDiastolic;
    }

    public Integer getBpSystolic() {
        return bpSystolic;
    }

    public void setBpSystolic(Integer bpSystolic) {
        this.bpSystolic = bpSystolic;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getNoOfFamilyMember() {
        return noOfFamilyMember;
    }

    public void setNoOfFamilyMember(Integer noOfFamilyMember) {
        this.noOfFamilyMember = noOfFamilyMember;
    }

    public DiseaseProfile getDiseaseProfile() {
        return diseaseProfile;
    }

    public void setDiseaseProfile(DiseaseProfile diseaseProfile) {
        this.diseaseProfile = diseaseProfile;
    }

    public Boolean getTakingMedicine() {
        return isTakingMedicine;
    }

    public void setTakingMedicine(Boolean takingMedicine) {
        isTakingMedicine = takingMedicine;
    }

    public Boolean getDoingCounseling() {
        return isDoingCounseling;
    }

    public void setDoingCounseling(Boolean doingCounseling) {
        isDoingCounseling = doingCounseling;
    }

    public Boolean getAdviceToGoAtHealthCenter() {
        return adviceToGoAtHealthCenter;
    }

    public void setAdviceToGoAtHealthCenter(Boolean adviceToGoAtHealthCenter) {
        this.adviceToGoAtHealthCenter = adviceToGoAtHealthCenter;
    }

    public Boolean getAdviceToGoAtSatellite() {
        return adviceToGoAtSatellite;
    }

    public void setAdviceToGoAtSatellite(Boolean adviceToGoAtSatellite) {
        this.adviceToGoAtSatellite = adviceToGoAtSatellite;
    }

    public Boolean getAdviceToGoAtCamp() {
        return adviceToGoAtCamp;
    }

    public void setAdviceToGoAtCamp(Boolean adviceToGoAtCamp) {
        this.adviceToGoAtCamp = adviceToGoAtCamp;
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

    public HealthCenter getCenter() {
        return center;
    }

    public void setCenter(HealthCenter center) {
        this.center = center;
    }

    public HaHome getHome() {
        return home;
    }

    public void setHome(HaHome home) {
        this.home = home;
    }
}
