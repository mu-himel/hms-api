package technology.grameen.gk.health.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long apiEmployeeId;
    private String fullName;
    private String designation;
    private Integer designationId;
    private String contactNumber;
    private String email;
    private Boolean isActive;
    private String employeeCode;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private HealthCenter center;

    @OneToOne(mappedBy = "employee")
    private EmployeeDetail employeeDetail;

    @OneToMany(mappedBy = "createdBy")
    @JsonBackReference(value = "patients")
    private Set<Patient> patients;

    @OneToMany(mappedBy = "createdBy")
    @JsonBackReference(value = "patientInvoices")
    private Set<PatientInvoice> patientInvoices;

    @OneToMany(mappedBy = "createdBy")
    private Set<LabTest> labTests;

    @OneToMany(mappedBy = "employee")
    private Set<EventPersonnel> eventPersonnels;

    @OneToMany(mappedBy = "employee")
    private Set<PatientOperation> patientOperations;

    @OneToMany(mappedBy = "employee")
    @OrderBy("latest DESC")
    private Set<JobHistory> jobHistories;

    @OneToMany(mappedBy = "doctor",fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private Set<Prescription> prescribes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;


    public Employee() {}

    public Employee(Long employeeId) {
        this.id = employeeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiEmployeeId() {
        return apiEmployeeId;
    }

    public void setApiEmployeeId(Long apiEmployeeId) {
        this.apiEmployeeId = apiEmployeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Integer designationId) {
        this.designationId = designationId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonBackReference
    public HealthCenter getCenter() {
        return center;
    }

    public void setCenter(HealthCenter center) {
        this.center = center;
    }


    public Set<Patient> getPatients() {
        return patients;
    }


    public void addPatient(Patient patient) {
        if(patient != null){
            if(this.patients == null){
                this.patients = new HashSet<>();
            }
            patient.setCreatedBy(this);
            this.patients.add(patient);
        }
    }


    public Set<PatientInvoice> getPatientInvoices() {
        return patientInvoices;
    }


    public void addPatientInvoice(PatientInvoice patientInvoice) {
        if(patientInvoice != null){
            if(this.patientInvoices == null){
                this.patientInvoices = new HashSet<>();
            }
            patientInvoice.setCreatedBy(this);
            this.patientInvoices.add(patientInvoice);
        }

    }

    @JsonBackReference
    @JsonIgnore
    public Set<Prescription> getPrescribes() {
        return prescribes;
    }

    public void setPrescribes(Set<Prescription> prescribes) {
        this.prescribes = prescribes;
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

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public EmployeeDetail getEmployeeDetail() {
        return employeeDetail;
    }

    public void setEmployeeDetail(EmployeeDetail employeeDetail) {
        this.employeeDetail = employeeDetail;
    }
}
