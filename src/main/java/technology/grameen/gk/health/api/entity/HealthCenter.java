package technology.grameen.gk.health.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "health_centers")
public class HealthCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long apiOfficeId;
    private String name;
    private String centerCode;
    private String address;
    private Boolean isActive;
    private Integer officeLevel;
    private String firstLevel;
    private String secondLevel;
    private String thirdLevel;
    private String fourthLevel;
    private String fifthLevel;

    @OneToMany(mappedBy = "center")
    private Set<Village> villages;

    private Integer officeTypeId;

    @OneToMany(mappedBy = "center")
    private Set<Employee> employees;

    @OneToMany(mappedBy = "center")
    private Set<Patient> patients;

    @OneToMany(mappedBy = "center",fetch = FetchType.LAZY)
    private Set<Prescription> prescriptions;

    @OneToMany(mappedBy = "center")
    private Set<PatientInvoice> patientInvoices;

    @OneToMany(mappedBy = "center")
    private Set<Event> events;

    @OneToMany(mappedBy = "center")
    private Set<PatientOperation>  patientOperations;

    @OneToMany(mappedBy = "heldOnCenter")
    private Set<PatientOperation> heldOnPatientOperations;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;

    public HealthCenter(){}

    public HealthCenter(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiOfficeId() {
        return apiOfficeId;
    }

    public void setApiOfficeId(Long apiOfficeId) {
        this.apiOfficeId = apiOfficeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCenterCode() {
        return centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonIgnore
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if(employee != null){
            if(this.employees == null){
                this.employees = new HashSet<>();
            }
            employee.setCenter(this);
            this.employees.add(employee);
        }

    }

    @JsonBackReference
    @JsonIgnore
    public Set<PatientInvoice> getPatientInvoices() {
        return patientInvoices;
    }

    public void addPatientInvoices(PatientInvoice patientInvoice) {

        if(patientInvoice != null){
            if(this.patientInvoices == null){
                this.patientInvoices = new HashSet<>();
            }
            patientInvoice.setCenter(this);
            this.patientInvoices.add(patientInvoice);
        }
    }

    @JsonBackReference
    public Set<Patient> getPatients() {
        return patients;
    }

    public void addPatient(Patient patient) {
        if(patient != null){
            if(this.patients == null){
                this.patients = new HashSet<>();
            }
            patient.setCenter(this);
            this.patients.add(patient);
        }
    }

    @JsonBackReference
    @JsonIgnore
    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void addPrescriptions(Prescription prescription) {
        if(prescription != null){
            if(this.prescriptions == null){
                this.prescriptions = new HashSet<>();
            }
            prescription.setCenter(this);
            this.prescriptions.add(prescription);
        }

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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getOfficeLevel() {
        return officeLevel;
    }

    public void setOfficeLevel(Integer officeLevel) {
        this.officeLevel = officeLevel;
    }

    public String getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    public String getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(String secondLevel) {
        this.secondLevel = secondLevel;
    }

    public String getThirdLevel() {
        return thirdLevel;
    }

    public void setThirdLevel(String thirdLevel) {
        this.thirdLevel = thirdLevel;
    }

    public String getFourthLevel() {
        return fourthLevel;
    }

    public void setFourthLevel(String fourthLevel) {
        this.fourthLevel = fourthLevel;
    }

    public String getFifthLevel() {
        return fifthLevel;
    }

    public void setFifthLevel(String fifthLevel) {
        this.fifthLevel = fifthLevel;
    }

    public Integer getOfficeTypeId() {
        return officeTypeId;
    }

    public void setOfficeTypeId(Integer officeTypeId) {
        this.officeTypeId = officeTypeId;
    }

    @JsonBackReference(value = "get-village")
    public Set<Village> getVillages() {
        return villages;
    }

    public void addVillage(Village village) {
       if(village!=null){
           if(this.villages==null){
               this.villages = new HashSet<>();
           }
           village.setCenter(this);
           this.villages.add(village);
       }
    }
}
