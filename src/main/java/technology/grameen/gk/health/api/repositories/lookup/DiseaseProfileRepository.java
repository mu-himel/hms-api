package technology.grameen.gk.health.api.repositories.lookup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.DiseaseProfile;
import technology.grameen.gk.health.api.entity.DiseaseType;

import java.util.List;

@Repository
public interface DiseaseProfileRepository extends JpaRepository<DiseaseProfile,Long> {

    @Query(value = "SELECT dp from DiseaseProfile dp JOIN FETCH dp.diseaseType dt")
    List<DiseaseProfileSimple> findAllProfiles();

    @Query(value = "SELECT dp FROM DiseaseProfile dp JOIN FETCH dp.diseaseType dt",
    countQuery = "SELECT COUNT(dp) FROM DiseaseProfile dp JOIN dp.diseaseType dt")
    Page<DiseaseProfileSimple> findAllProfiles(Pageable pageable);

    interface DiseaseProfileSimple{
        Long getId();
        String getName();
        String getAlias();
        Boolean getIsFollowUp();
    }
    List<DiseaseProfileSimple> findByDiseaseType(DiseaseType diseaseType);

}
