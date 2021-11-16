package technology.grameen.gk.health.api.repositories.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.DiseaseProfile;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.entity.PatientDiseaseProfile;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientDiseaseProfileRepository extends JpaRepository<PatientDiseaseProfile,Long> {

    interface Patient{
        Long getId();
    }
    interface PatientDiseaseProfileInfo{
        Long getId();
        DiseaseProfile getDiseaseProfile();

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate getStartDate();

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate getEndDate();
        Boolean getActive();
        HealthCenter getCenter();
        Patient getPatient();
        Integer getFollowup();

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate getCreatedAt();

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate getUpdatedAt();

    }
    @Query(value = "SELECT pdp from PatientDiseaseProfile pdp JOIN FETCH pdp.patient p " +
            "LEFT JOIN FETCH pdp.diseaseProfile dp" +
            "LEFT JOIN FETCH p.home h " +
            "LEFT JOIN FETCH p.village v LEFT JOIN FETCH v.center " +
            "WHERE p.id = :id AND pdp.isActive=true")
    List<PatientDiseaseProfileInfo> findByPatientId(@Param("id") Long id);
}
