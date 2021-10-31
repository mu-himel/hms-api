package technology.grameen.gk.health.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @ManyToOne
    @JoinColumn(name = "service_category_id",referencedColumnName = "id")
    private ServiceCategory serviceCategory;

    @OneToMany(mappedBy = "service")
    private Set<PatientServiceDetail> patientServiceDetails;

    @OneToMany(mappedBy = "service", cascade = CascadeType.REMOVE)
    @OrderBy("displayOrder ASC")
    private Set<LabTestAttribute> labTestAttributes = new HashSet<>();

    @OneToMany(mappedBy = "service")
    private Set<LabTest> labTests;

    @ManyToOne
    @JoinColumn(name = "lab_test_group_id",referencedColumnName = "id")
    private LabTestGroup labTestGroup;

    private String name;

    private String code;
    private String serviceCode;
    private BigDecimal currentCost;
    private BigDecimal currentGbCost;
    private String description;
    private Boolean isActive;
    private Boolean isLabTest;
    private Boolean isPrescription;
    private Boolean fieldSaleable;
    private BigDecimal fieldCost;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;

    public Service(){}

    public Service(Long serviceId,
                   ServiceCategory serviceCategory,
                   LabTestGroup labTestGroup, String name, String code,
                   BigDecimal currentCost, BigDecimal currentGbCost,
                   String description, Boolean isActive, Boolean isLabTest,
                   LocalDateTime createdAt, LocalDateTime lastUpdatedAt
    ) {

        this.serviceId = serviceId;
        this.serviceCategory = serviceCategory;
        this.labTestGroup = labTestGroup;
        this.name = name;
        this.code = code;
        this.currentCost = currentCost;
        this.currentGbCost = currentGbCost;
        this.description = description;
        this.isActive = isActive;
        this.isLabTest = isLabTest;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(BigDecimal currentCost) {
        this.currentCost = currentCost;
    }

    public BigDecimal getCurrentGbCost() {
        return currentGbCost;
    }

    public void setCurrentGbCost(BigDecimal currentGbCost) {
        this.currentGbCost = currentGbCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getLabTest() {
        return isLabTest;
    }

    public void setLabTest(Boolean labTest) {
        this.isLabTest = labTest;
    }


    public LabTestGroup getLabTestGroup() {
        return labTestGroup;
    }

    public void setLabTestGroup(LabTestGroup labTestGroup) {
        this.labTestGroup = labTestGroup;
    }

    @JsonBackReference("patient-service-details")
    @JsonIgnore
    public Set<PatientServiceDetail> getPatientServiceDetails() {
        return patientServiceDetails;
    }

    public void addPatientService(PatientServiceDetail patientServiceDetail) {

        if(patientServiceDetail != null){
            if(this.patientServiceDetails == null){
                this.patientServiceDetails = new HashSet<>();
            }
            patientServiceDetail.setService(this);
            this.patientServiceDetails.add(patientServiceDetail);
        }
    }

    @OrderBy("displayOrder ASC")
    public Set<LabTestAttribute> getLabTestAttributes() {
        return labTestAttributes;
    }

    public void addLabTestAttribute(LabTestAttribute labTestAttribute) {
        if(labTestAttribute != null){
            if(this.labTestAttributes == null){
                this.labTestAttributes = new HashSet<>();
            }
            labTestAttribute.setService(this);
            this.labTestAttributes.add(labTestAttribute);
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

    public Boolean getPrescription() {
        return isPrescription;
    }

    public void setPrescription(Boolean prescription) {
        isPrescription = prescription;
    }

    @JsonBackReference("lab-test")
    public Set<LabTest> getLabTests() {
        return labTests;
    }

    public void addLabTest(LabTest labTest) {
        if(labTest != null) {
            if (this.labTests == null) {
                this.labTests = new HashSet<>();
            }
            labTest.setService(this);
            this.labTests.add(labTest);
        }
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Boolean getFieldSaleable() {
        return fieldSaleable;
    }

    public void setFieldSaleable(Boolean fieldSaleable) {
        this.fieldSaleable = fieldSaleable;
    }

    public BigDecimal getFieldCost() {
        return fieldCost;
    }

    public void setFieldCost(BigDecimal fieldCost) {
        this.fieldCost = fieldCost;
    }
}
