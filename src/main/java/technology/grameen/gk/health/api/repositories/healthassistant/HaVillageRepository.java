package technology.grameen.gk.health.api.repositories.healthassistant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.HaVillage;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.entity.Village;

import java.util.List;
import java.util.Optional;

@Repository
public interface HaVillageRepository extends JpaRepository<HaVillage,Long> {

    List<HaVillage> findByCenterAndVillage(HealthCenter center, Village village);

    interface HaPatient{
        String getHealthAssistant();
        String getPatient();
        String getPid();
        String getVillage();
        String getAddress();
    }




    @Query(value = "SELECT e.FULL_NAME healthAssistant, p.FULL_NAME patient, p.pid,lv.VILLAGE_NAME village, " +
            "p.STREET_ADDRESS address \n" +
            "FROM PATIENTS p\n" +
            "INNER JOIN  HA_HOMES hh ON p.HOME_ID  = hh.ID \n" +
            "INNER JOIN LG_VILLAGES lv ON hh.VILLAGE_LG_VILLAGE_ID   =  lv.LG_VILLAGE_ID\n" +
            "INNER JOIN EMPLOYEES e ON hh.HEALTH_ASSISTANT_ID = e.ID\n" +
            "WHERE e.id = :employeeId AND hh.HEALTH_ASSISTANT_ID = :employeeId \n",
    nativeQuery = true)
    Page<HaPatient> findPatientsByEmployeeId(@Param("employeeId") Long employeeId, Pageable pageable);
}
