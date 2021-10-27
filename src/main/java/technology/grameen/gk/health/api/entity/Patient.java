package technology.grameen.gk.health.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Village village;
    private String streetAddress;
    private String pid;
    private String fullName;
    private String guardianName;
    private String spouseName;
    private String motherName;
    private String gender;
    private String maritalStatus;
    private String mobileNumber;
    private String email;
    private String age;

    private Boolean isGB;

    private LocalDate dob;

    @ManyToOne
    private HaHome home;

    @ManyToOne
    @JoinColumn(columnDefinition = "center_id",referencedColumnName = "id")
    private HealthCenter center;

    @OneToOne(mappedBy = "patient")
    private PatientDetail detail;

    @OneToOne(mappedBy = "patient")
    private CardRegistration registration;

    @OneToMany(mappedBy = "patient")
    private Set<CardRegistrationLog> registrationLogs;

    @OneToMany(mappedBy = "prescriptionPatient")
    private Set<Prescription> prescriptions;

    @OneToMany(mappedBy = "patient")
    @OrderBy("id DESC")
    private Set<PatientInvoice> patientInvoices;

    @OneToMany(mappedBy = "patient")
    private Set<LabTest> labTests;

    @OneToOne(mappedBy = "patient")
    private CardMember cardMember;

    @ManyToOne
    @JoinColumn(columnDefinition = "created_by",referencedColumnName = "id")
    private Employee createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;

    public Patient() {

    }

    public Patient(Long id) {
        this.id = id;
    }

    public Patient(Long id, String pid, String fullName, String gender, String maritalStatus,
                   String age, HealthCenter center, String guardianName,
                   LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.pid = pid;
        this.fullName = fullName;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.age = age;
        this.center = center;
        this.guardianName = guardianName;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public HealthCenter getCenter() {
        return center;
    }

    public void setCenter(HealthCenter center) {
        this.center = center;
    }

    public PatientDetail getDetail() {
        return detail;
    }

    public void setDetail(PatientDetail detail) {
        this.detail = detail;
    }


    public CardRegistration getRegistration() {
        return registration;
    }

    public void addRegistration(CardRegistration registration) {
        if(registration != null){
//            if(this.registrations == null){if(this.registrations == null){
//                this.registrations = new HashSet<>();
//            }
            if(this.registration == null) {
                this.registration = registration;
            }
            registration.setPatient(this);
//            this.registrations.add(registration);
        }



    }


    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }


    public Set<PatientInvoice> getPatientInvoices() {
        return patientInvoices;
    }

    public void addPatientInvoices(PatientInvoice patientInvoice) {
        if(patientInvoice != null){
            if(this.patientInvoices == null){
                this.patientInvoices = new HashSet<>();
            }
            patientInvoice.setPatient(this);
            this.patientInvoices.add(patientInvoice);
        }
    }

    public Boolean getGB() {
        return isGB;
    }

    public void setGB(Boolean GB) {
        isGB = GB;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HaHome getHome() {
        return home;
    }

    public void setHome(HaHome home) {
        this.home = home;
    }

    public void setRegistration(CardRegistration registration) {
        this.registration = registration;
    }

    public Set<CardRegistrationLog> getRegistrationLogs() {
        return registrationLogs;
    }

    public void setRegistrationLogs(Set<CardRegistrationLog> registrationLogs) {
        this.registrationLogs = registrationLogs;
    }

    public void setPatientInvoices(Set<PatientInvoice> patientInvoices) {
        this.patientInvoices = patientInvoices;
    }

    public Set<LabTest> getLabTests() {
        return labTests;
    }

    public void setLabTests(Set<LabTest> labTests) {
        this.labTests = labTests;
    }

    public CardMember getCardMember() {
        return cardMember;
    }

    public void setCardMember(CardMember cardMember) {
        this.cardMember = cardMember;
    }
}
