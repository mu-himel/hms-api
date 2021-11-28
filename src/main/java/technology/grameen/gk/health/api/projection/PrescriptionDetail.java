package technology.grameen.gk.health.api.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import technology.grameen.gk.health.api.entity.*;

import javax.persistence.OrderBy;
import java.time.LocalDateTime;
import java.util.List;

public interface PrescriptionDetail {

    interface Center {
        Long getId();
        String getName();
        String getCenterCode();
    }

    interface EmployeeDetail{
        Long getId();
        String getBmdcRegNumber();
        String getPrescriptionDegreeInst();
    }

    interface Doctor{
        Long getId();
        String getFullName();
        String getDesignation();
        EmployeeDetail getEmployeeDetail();
    }

    interface Service{
        Long getServiceId();
        String getName();
    }

    interface RecommendedTest{
        Long getId();
        Service getService();
    }

    interface RecommendedMedicine{
        Long getId();
        Medicine getMedicine();
        Integer getDuration();
        String getDurationUnit();
        Integer getRule();
        String getDose();
        String getMedicineType();
    }

    interface CardRegistration{
        Long getId();
        Boolean getGB();
    }
    interface CardMember{
        Long getId();
       CardRegistration getCardRegistration();

    }
    interface Patient{
        Long getId();
        String getFullName();
        String getAge();
        String getGender();
        Boolean getGB();
        String getMobileNumber();
        CardRegistration getRegistration();
        CardMember getCardMember();
        String getPid();
    }

    interface Employee{
        Long getId();
        String getFullName();
    }

    interface HealthCenter{
        Long getId();
        String getName();
        String getThirdLevel();
        String getCenterCode();
    }

    interface Invoice{
        Long getId();
        String getInvoiceNumber();
    }

    Center getCenter();
    Doctor getDoctor();
    String getpNumber();


    List<RecommendedMedicine> getRecommendedMedicines();
    List<RecommendedTest> getRecommendedTests();
    String getSymptoms();
    String getDiseaseProfile();

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime getVisitDate();
    Boolean getNew();
    String getAdvice();
    String getAdditionalTests();

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime getCreatedAt();

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime getLastFreeVisitDate();
    String getFilePath();
    Boolean getTeleCall();
    GeneralExamination getGeneralExamination();
    FamilyHistory getFamilyHistory();
    PersonalHistory getPersonalHistory();
    Patient getPrescriptionPatient();
    Long getId();
    Boolean getRefer();
    Employee getCallTo();

    Hospital getReferHospital();
    Invoice getPatientInvoice();
}
