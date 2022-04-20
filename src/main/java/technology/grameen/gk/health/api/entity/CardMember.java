package technology.grameen.gk.health.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "card_members")
public class CardMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private Integer age;
    private String ageMonth;
    private String ageDays;
    private LocalDate dob;
    private String gender;
    private String bloodGroup;
    private String nationality;
    private String nationalId;
    private String occupation;
    private String relationWithPatient;
    private String mobileNumber;
    private String email;

    @OneToOne
    private Patient patient;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;

    @ManyToOne
    @JoinColumn(name = "card_registration_id", referencedColumnName = "id")
    private CardRegistration cardRegistration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getRelationWithPatient() {
        return relationWithPatient;
    }

    public void setRelationWithPatient(String relationWithPatient) {
        this.relationWithPatient = relationWithPatient;
    }

    @JsonBackReference
    public CardRegistration getCardRegistration() {
        return cardRegistration;
    }

    public void setCardRegistration(CardRegistration cardRegistration) {
        this.cardRegistration = cardRegistration;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
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
