package technology.grameen.gk.health.api.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import technology.grameen.gk.health.api.entity.CardRegistration;
import technology.grameen.gk.health.api.entity.DiseaseType;
import technology.grameen.gk.health.api.entity.PatientServiceDetail;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface PatientInvoiceDetail {

    Long getId();
    String getInvoiceNumber();
    BigDecimal getPaidAmount();
    BigDecimal getPayableAmount();

    @JsonFormat(pattern = "YYYY-MM-dd")
    LocalDateTime getCreatedAt();



    interface CardMemberCardRegistration{
        Long getId();
        Boolean getGB();

        String getCardNumber();
        Integer getValidityDuration();
        Integer getTotalServiceTaken();
        Boolean getActive();
        LocalDateTime getStartDate();
        LocalDateTime getExpiredDate();
    }

    interface CardMember{
        Long getId();
        String getFullName();
        CardMemberCardRegistration getCardRegistration();
        Patient getPatient();

    }

    interface CardRegistration{
        Long getId();
        Boolean getActive();
        String getCardNumber();

        Boolean getGB();
        Integer getValidityDuration();

        Integer getTotalServiceTaken();
        LocalDateTime getStartDate();
        LocalDateTime getExpiredDate();
        void setActive(Boolean active);
    }





    interface Patient{

        Long getId();
        String getPid();
        String getFullName();
        String getGuardianName();
        String getMotherName();
        String getGender();
        String getMobileNumber();
        String getAge();
        Boolean getGB();
        String getBloodPressure();
        String getPulse();
        String getTemperature();
        String getWeight();
        CardRegistration getRegistration();

    }

    interface ServiceCategory{
        Long getId();
        String getName();
        Boolean getPrintable();
    }

    interface LabTestGroup{
        Long getId();
        String getName();
    }

    interface Service{
        Long getServiceId();
        String getName();
        String getServiceCode();
        Boolean getLabTest();
        Boolean getGroupable();
        LabTestGroup getLabTestGroup();
        ServiceCategory getServiceCategory();
    }

    interface DiseaseType{
        Long getId();
        String getName();
        String getAlias();
    }
    interface PatientServiceDetail{
        Long getId();
        Service getService();
        Integer getRoomNumber();
        String getReferredDoctor();
        Integer getServiceQty();
        BigDecimal getServiceAmount();
        BigDecimal getDiscountAmount();
        BigDecimal getPayableAmount();
        LocalDateTime getCreatedAt();
        LocalDateTime getLastUpdatedAt();
        Boolean getReportGenerated();
        DiseaseType getDiseaseType();
    }

    Patient getPatient();

    interface EmployeeDetail{
        Long getId();
        String getBmdcRegNumber();
        String getPrescriptionDegreeInst();
    }

    interface Employee{
        Long getId();
        String getFullName();
        EmployeeDetail getEmployeeDetail();
    }

    Employee getCreatedBy();

    List<PatientServiceDetail> getPatientServiceDetails();

    interface HealthCenter{
        Long getId();
        String getName();
        String getCenterCode();
    }
    HealthCenter getCenter();

}



