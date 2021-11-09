package technology.grameen.gk.health.api.repositories.healthassistant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.HaVillage;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.entity.Village;

import java.util.List;

@Repository
public interface HaVillageRepository extends JpaRepository<HaVillage,Long> {

    List<HaVillage> findByCenterAndVillage(HealthCenter center, Village village);
}
