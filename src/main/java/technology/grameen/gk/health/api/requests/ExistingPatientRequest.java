package technology.grameen.gk.health.api.requests;

import technology.grameen.gk.health.api.entity.CardRegistration;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.entity.Village;

import java.time.LocalDate;
import java.util.List;

public class ExistingPatientRequest {

    private Integer age;
    private HealthCenter center;
    private Village village;
    private LocalDate dob;
    private String fullName;
    private Boolean gb;
    private String gender;

    private CardRegistration registration;

    private Employee createdBy;

    public ExistingPatientRequest() {

    }

    public ExistingPatientRequest(Integer age, HealthCenter center, String dob,
                                  String fullName, Boolean gb, String gender,
                                  CardRegistration registration,
                                  Employee createdBy) {
        this.age = age;
        this.center = center;
        this.dob = LocalDate.parse(dob);
        this.fullName = fullName;
        this.gb = gb;
        this.gender = gender;
        this.registration = registration;
        this.createdBy = createdBy;
    }



    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }



    public HealthCenter getCenter() {
        return center;
    }

    public void setCenter(HealthCenter center) {
        this.center = center;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = LocalDate.parse(dob);
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getGb() {
        return gb;
    }

    public void setGb(Boolean gb) {
        this.gb = gb;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public CardRegistration getRegistration() {
        return registration;
    }

    public void setRegistration(CardRegistration registration) {
        this.registration = registration;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }
}
