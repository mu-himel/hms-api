package technology.grameen.gk.health.api.requests;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import technology.grameen.gk.health.api.entity.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;


public class PatientRequest {


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
    private Integer age;
    private String ageMonth;
    private String ageDays;
    private LocalDate dob;

    private HealthCenter center;

    private PatientDetail detail;

    private Employee createdBy;

    private CardRegistration cardRegistration;

    private HaHome home;

    private Boolean gb;

    private LocalDateTime createdAt;

    public PatientRequest(){}

    public PatientRequest(Patient cardMember){
        this.fullName = cardMember.getFullName();
        this.age = (cardMember.getAge()!=null)?Integer.parseInt(cardMember.getAge()):null;
        this.dob = cardMember.getDob();
        this.gender = cardMember.getGender();
        this.mobileNumber = cardMember.getMobileNumber();
        this.center = cardMember.getCenter();
        this.home = cardMember.getHome();
        this.createdBy = cardMember.getCreatedBy();
        this.village = cardMember.getVillage();
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime dateTime = LocalDateTime.parse(dateOfBirth, formatter);
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

    public Employee getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
    }

    public CardRegistration getCardRegistration() {
        return cardRegistration;
    }

    public void setCardRegistration(CardRegistration cardRegistration) {
        this.cardRegistration = cardRegistration;
    }

    public Boolean getGb() {
        return gb;
    }

    public void setGb(Boolean gb) {
        this.gb = gb;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public String getSecondaryMobileNumber() {
        return secondaryMobileNumber;
    }

    public void setSecondaryMobileNumber(String secondaryMobileNumber) {
        this.secondaryMobileNumber = secondaryMobileNumber;
    }

    public String getAgeMonth() {
        return ageMonth;
    }

    public void setAgeMonth(String ageMonth) {
        this.ageMonth = ageMonth;
    }

    public String getAgeDays() {
        return ageDays;
    }

    public void setAgeDays(String ageDays) {
        this.ageDays = ageDays;
    }
}
