package technology.grameen.gk.health.api.requests;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import technology.grameen.gk.health.api.entity.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class InvoiceCreate {

    private Long id;


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
    private String secondaryMobileNumber;
    private String email;
    private String age;

    private Boolean isGB;

    private LocalDate dob;

    private Boolean isLegacy;

    private String bloodPressure;
    private String temperature;
    private String pulse;
    private String weight;

    private HaHome home;

    private HealthCenter center;


    private PatientDetail detail;


    private CardRegistration registration;


    private Set<CardRegistrationLog> registrationLogs;


    private Set<Prescription> prescriptions;


    private Set<PatientInvoiceRequest> patientInvoices;


    private Set<LabTest> labTests;


    private CardMember cardMember;


    private Employee createdBy;


    private LocalDateTime createdAt;


    private LocalDateTime lastUpdatedAt;

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

    public String getSecondaryMobileNumber() {
        return secondaryMobileNumber;
    }

    public void setSecondaryMobileNumber(String secondaryMobileNumber) {
        this.secondaryMobileNumber = secondaryMobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Boolean getGB() {
        return isGB;
    }

    public void setGB(Boolean GB) {
        isGB = GB;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Boolean getLegacy() {
        return isLegacy;
    }

    public void setLegacy(Boolean legacy) {
        isLegacy = legacy;
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

    public PatientDetail getDetail() {
        return detail;
    }

    public void setDetail(PatientDetail detail) {
        this.detail = detail;
    }

    public CardRegistration getRegistration() {
        return registration;
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

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Set<PatientInvoiceRequest> getPatientInvoices() {
        return patientInvoices;
    }

    public void setPatientInvoices(Set<PatientInvoiceRequest> patientInvoices) {
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

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
