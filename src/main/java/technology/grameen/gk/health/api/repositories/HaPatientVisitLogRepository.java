package technology.grameen.gk.health.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.HaPatientVisitLog;


@Repository
public interface HaPatientVisitLogRepository extends JpaRepository<HaPatientVisitLog,Long> {

    interface PageVisitLog{
        Long getId();
        Integer getBpDiastolic();
        Integer getBpSystolic();
        Integer getNoOfFamilyMember();
        String getHeathAssistantName();
        String getPatientName();
        String getDiseaseProfile();

    }
    @Query(value = "Select hplog.id, hplog.bp_diastolic as bpDiastolic, hplog.bp_systolic as bpSystolic, " +
            " hplog.no_of_family_member noOfFamilyMember, e.full_name as heathAssistantName, p.full_name as patientName, " +
            " dp.name diseaseProfile FROM ha_patient_visit_logs hplog" +
            " JOIN employees e ON e.id = hplog.health_assistant_id" +
            " JOIN patients p on p.id=hplog.patient_id " +
            " JOIN disease_profiles dp on dp.id = hplog.disease_profile_id",
            countQuery = "select count(hplog.id) FROM ha_patient_visit_logs hplog" +
                    " JOIN employees e ON e.id = hplog.health_assistant_id" +
                    " JOIN patients p on p.id = hplog.patient_id " +
                    " JOIN disease_profiles dp on dp.id = hplog.disease_profile_id",
            nativeQuery = true)
    Page<PageVisitLog> getAll(Pageable pageable);
}
