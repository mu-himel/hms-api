package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.DiseaseProfile;
import technology.grameen.gk.health.api.entity.DiseaseType;

import java.util.List;

@Repository
public interface DiseaseProfileRepository extends JpaRepository<DiseaseProfile,Long> {

    interface DiseaseProfileSimple{
        Long getId();
        String getName();
        String getAlias();
    }
    List<DiseaseProfileSimple> findByDiseaseType(DiseaseType diseaseType);

}
