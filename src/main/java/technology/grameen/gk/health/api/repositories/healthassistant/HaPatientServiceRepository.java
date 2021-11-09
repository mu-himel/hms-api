package technology.grameen.gk.health.api.repositories.healthassistant;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.HaPatientService;
import technology.grameen.gk.health.api.entity.Patient;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HaPatientServiceRepository extends JpaRepository<HaPatientService,Long> {

    List<HaPatientService> findByCreatedAt(LocalDateTime date);




    interface IHaPatientService{
        Long getId();

        Long getPatientId();

        String getResult();

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate getCreatedAt();
        Long getServiceId();
        Long getHealthAssistantId();
    }


    @Query(value = "SELECT id,created_at createdAt, health_assistant_id healthAssistantId," +
            "service_service_id serviceId, patient_id patientId, result from HA_PATIENT_SERVICES hps" +
            " WHERE hps.patient_id = :patient AND TO_CHAR(hps.created_at,'YYYY-MM-DD')=:dt ",nativeQuery = true)
    List<IHaPatientService> findByPatientAndCreatedAtContaining(@Param("patient") Long patient,
                                                                @Param("dt") String date);
}
