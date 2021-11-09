package technology.grameen.gk.health.api.repositories.healthassistant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.HaHome;
import technology.grameen.gk.health.api.entity.Village;

import java.util.List;

@Repository
public interface HaHomeRepository extends JpaRepository<HaHome,Long> {

    interface HaHomeItem{
        Long getId();
        HVillage getVillage();
        String getHomeName();
        Employee getHealthAssistant();
    }

    interface HVillage{
        Long getLgVillageId();
    }
    interface Employee{
        Long getId();
    }
    List<HaHomeItem> findByVillage(Village village);
}
