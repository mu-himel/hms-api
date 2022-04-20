package technology.grameen.gk.health.api.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import technology.grameen.gk.health.api.entity.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public interface PatientSearchResult {

        interface Village{
                Long getLgVillageId();
                String getVillageName();
        }

        interface Service{
                Long getServiceId();
                String getName();
                Boolean getLabTest();
                Boolean getPrescription();
                Boolean getFieldSaleable();
        }

        interface PatientServiceDetail{
                Long getId();
                Service getService();
                Integer getServiceQty();
                Integer getRoomNumber();
                String getReferredDoctor();
                BigDecimal getServiceAmount();
                BigDecimal getDiscountAmount();
                BigDecimal getPayableAmount();
                Boolean getRefunded();
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime getCreatedAt();
                Boolean getReportGenerated();
        }

        interface PatientInvoice{
                Long getId();
                String getInvoiceNumber();
                Set<PatientServiceDetail> getPatientServiceDetails();
                BigDecimal getServiceAmount();
                BigDecimal getDiscountAmount();
                BigDecimal getPayableAmount();
                BigDecimal getPaidAmount();
                BigDecimal getDueAmount();

                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime getCreatedAt();
                HealthCenter getCenter();
        }

        interface Patient{
                String getFullName();
                String getPid();
                Boolean getGB();
                Long getId();
        }

        interface CardRegistration{
                Long getId();
                Boolean getActive();
                String getCardNumber();
                Patient getPatient();
                Boolean getGB();
                Integer getValidityDuration();
                Set<CardMember> getMembers();
                Integer getTotalServiceTaken();

                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime getStartDate();

                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime getExpiredDate();
                void setActive(Boolean active);
        }

        interface CardMemberCardRegistration{
                Long getId();
                Boolean getGB();
                Patient getPatient();
                String getCardNumber();
                Integer getValidityDuration();
                Integer getTotalServiceTaken();
                Boolean getActive();

                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime getStartDate();

                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime getExpiredDate();
        }

        interface CardMember{
                Long getId();
                String getFullName();
                CardMemberCardRegistration getCardRegistration();
                Patient getPatient();
                String getGender();
                String getAge();
                String getAgeMonth();
                String getAgeDays();
                @JsonFormat(pattern = "yyyy-MM-dd")
                LocalDate getDob();
                String getRelationWithPatient();

        }

        interface HealthCenter{
           Long getId();
           String getName();
           String getCenterCode();
        }


        Long getId();
        String getPid();
        String getFullName();
        Village getVillage();
        HealthCenter getCenter();
        String getStreetAddress();
        String getGuardianName();
        String getSpouseName();
        String getMotherName();
        String getGender();
        String getMobileNumber();
        String getSecondaryMobileNumber();
        String getEmail();
        String getAge();
        String getAgeMonth();
        String getAgeDays();
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate getDob();
        CardRegistration getRegistration();
        void addRegistration(CardRegistration cardRegistration);
        Set<PatientInvoice> getPatientInvoices();
        CardMember getCardMember();
        String getMaritalStatus();
        PatientDetail getDetail();
        HaHome getHome();
        Boolean getGB();
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime getCreatedAt();

}
