package technology.grameen.gk.health.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient prescriptionPatient;

    @OneToOne
    private PatientInvoice patientInvoice;

    @ManyToOne
    @JoinColumn(name = "doctor_id",referencedColumnName = "id")
    @JsonBackReference
    private Employee doctor;

    @ManyToOne
    private HealthCenter center;

    private String pNumber;
    private LocalDateTime visitDate;
    private LocalDateTime nextVisitDate;
    private LocalDateTime lastFreeVisitDate;

    private String symptoms;
    private String diseaseProfile;

    @OneToOne(mappedBy = "prescription")
    private FamilyHistory familyHistory;

    @OneToOne(mappedBy = "prescription")
    private PersonalHistory personalHistory;

    @OneToOne(mappedBy = "prescription")
    private GeneralExamination generalExamination;

    @OneToMany(mappedBy = "prescription")
    private Set<RecommendedTest> recommendedTests;

    @OneToMany(mappedBy = "prescription")
    private Set<RecommendedMedicine> recommendedMedicines;

    private Boolean isNew;
    private Boolean isTeleCall = false;
    private Boolean isRefer = false;

    @ManyToOne
    private Employee callTo;

    @ManyToOne
    private Hospital referHospital;

    @Column(length = 1000)
    private String additionalTests;

    @Column(length = 500)
    private String advice;

    @Column(length = 1000)
    private String filePath;


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

    public Patient getPrescriptionPatient() {
        return prescriptionPatient;
    }

    public void setPrescriptionPatient(Patient prescriptionPatient) {
        this.prescriptionPatient = prescriptionPatient;
    }

    @JsonBackReference
    public Employee getDoctor() {
        return doctor;
    }

    public void setDoctor(Employee doctor) {
        this.doctor = doctor;
    }

    public HealthCenter getCenter() {
        return center;
    }

    public void setCenter(HealthCenter center) {
        this.center = center;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    public LocalDateTime getNextVisitDate() {
        return nextVisitDate;
    }

    public void setNextVisitDate(LocalDateTime nextVisitDate) {
        this.nextVisitDate = nextVisitDate;
    }

    public LocalDateTime getLastFreeVisitDate() {
        return lastFreeVisitDate;
    }

    public void setLastFreeVisitDate(LocalDateTime lastFreeVisitDate) {
        this.lastFreeVisitDate = lastFreeVisitDate;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getTeleCall() {
        return isTeleCall;
    }

    public void setTeleCall(Boolean teleCall) {
        isTeleCall = teleCall;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
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

    public PatientInvoice getPatientInvoice() {
        return patientInvoice;
    }

    public void setPatientInvoice(PatientInvoice patientInvoice) {
        this.patientInvoice = patientInvoice;
    }

    public FamilyHistory getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(FamilyHistory familyHistory) {
        this.familyHistory = familyHistory;
    }

    public PersonalHistory getPersonalHistory() {
        return personalHistory;
    }

    public void setPersonalHistory(PersonalHistory personalHistory) {
        this.personalHistory = personalHistory;
    }

    public GeneralExamination getGeneralExamination() {
        return generalExamination;
    }

    public void setGeneralExamination(GeneralExamination generalExamination) {
        this.generalExamination = generalExamination;
    }

    public Set<RecommendedTest> getRecommendedTests() {
        return recommendedTests;
    }

    public void addRecommendedTest(RecommendedTest recommendedTest) {
        if(recommendedTest != null){
            if(this.recommendedTests == null){
                this.recommendedTests = new HashSet<>();
            }
            recommendedTest.setPrescription(this);
            this.recommendedTests.add(recommendedTest);
        }
    }

    public Set<RecommendedMedicine> getRecommendedMedicines() {
        return recommendedMedicines;
    }

    public void addRecommendedMedicine(RecommendedMedicine recommendedMedicine) {
        if(recommendedMedicine != null){
            if(this.recommendedMedicines == null){
                this.recommendedMedicines = new HashSet<>();
            }
            this.recommendedMedicines.add(recommendedMedicine);
        }

    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Employee getCallTo() {
        return callTo;
    }

    public void setCallTo(Employee callTo) {
        this.callTo = callTo;
    }

    public Boolean getRefer() {
        return isRefer;
    }

    public void setRefer(Boolean refer) {
        isRefer = refer;
    }

    public Hospital getReferHospital() {
        return referHospital;
    }

    public void setReferHospital(Hospital referHospital) {
        this.referHospital = referHospital;
    }

    public String getDiseaseProfile() {
        return diseaseProfile;
    }

    public void setDiseaseProfile(String diseaseProfile) {
        this.diseaseProfile = diseaseProfile;
    }

    public String getAdditionalTests() {
        return additionalTests;
    }

    public void setAdditionalTests(String additionalTests) {
        this.additionalTests = additionalTests;
    }
}
