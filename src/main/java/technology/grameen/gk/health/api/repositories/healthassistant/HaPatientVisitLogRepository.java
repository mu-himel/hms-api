package technology.grameen.gk.health.api.repositories.healthassistant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.HaPatientVisitLog;

import java.util.Optional;


@Repository
public interface HaPatientVisitLogRepository extends JpaRepository<HaPatientVisitLog,Long> {

    interface PageVisitLogDetail{
        Long getId();
        Integer getBpDiastolic();
        Integer getBpSystolic();
        Integer getNoOfFamilyMember();
        String getPatientType();
        Integer getPatientTypeId();
        Double getHeight();
        Double getWeight();
        Integer getAdviceToGoAtCamp();
        Integer getAdviceToGoAtHealthCenter();
        Integer getAdviceToGoAtSatellite();
        Integer getTakingMedicine();
        Integer getDoingCounseling();


    }

    @Query(value = "select hapvl.id,bp_diastolic bpDiastolic, bp_systolic bpSystolic, no_of_family_member noOfFamilyMember, " +
            "health_assistant_id healthAssistantId, hapvl.patient_id patientId, weight, height," +
            " advice_to_go_at_camp adviceToGoAtCamp, advice_to_go_at_health_center adviceToGoAtHealthCenter," +
            " advice_to_go_at_satellite adviceToGoAtSatellite, dp.name patientType, dp.id patientTypeId," +
            " is_taking_medicine takingMedicine, is_doing_counseling doingCounseling" +
            " FROM ha_patient_visit_logs hapvl JOIN disease_profiles dp ON dp.id = hapvl.disease_profile_id" +
            " WHERE hapvl.center_id =:centerId AND hapvl.patient_id = :pid AND TO_CHAR(hapvl.created_at,'yyyy-mm-dd')=:dt",
    nativeQuery = true)
    Optional<PageVisitLogDetail> findVisitLogByPatientAndDate(@Param("centerId") Long centerId,
                                                        @Param("pid") Long pid,
                                                        @Param("dt") String dt);

    interface PageVisitLog{
        Long getId();
        Integer getBpDiastolic();
        Integer getBpSystolic();
        Integer getNoOfFamilyMember();
        String getHeathAssistantName();
        String getPatientName();
        String getDiseaseProfile();
        Double getHeight();
        Double getWeight();



    }
    @Query(value = "Select hplog.id, hplog.bp_diastolic as bpDiastolic, hplog.bp_systolic as bpSystolic, " +
            " hplog.no_of_family_member noOfFamilyMember, e.full_name as heathAssistantName, p.full_name as patientName, " +
            " dp.name diseaseProfile FROM ha_patient_visit_logs hplog" +
            " JOIN employees e ON e.id = hplog.health_assistant_id" +
            " JOIN patients p on p.id=hplog.patient_id " +
            " LEFT JOIN disease_profiles dp on dp.id = hplog.disease_profile_id",
            countQuery = "select count(hplog.id) FROM ha_patient_visit_logs hplog" +
                    " JOIN employees e ON e.id = hplog.health_assistant_id" +
                    " JOIN patients p on p.id = hplog.patient_id " +
                    " JOIN disease_profiles dp on dp.id = hplog.disease_profile_id",
            nativeQuery = true)
    Page<PageVisitLog> getAll(Pageable pageable);
}
