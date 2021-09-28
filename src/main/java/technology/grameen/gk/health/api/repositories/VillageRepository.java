package technology.grameen.gk.health.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.entity.Village;
import technology.grameen.gk.health.api.projection.VillageListItem;

import java.util.List;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {

    @Query(value = "select v from Village v join fetch v.center c where v.unionId=:unionId")
    List<Village> findByUnionId(@Param("unionId") Long unionId);

    @Query(value = "select v from Village v join fetch v.center c where c=:center")
    List<Village> findByCenter(@Param("center") HealthCenter center);

    Page<VillageListItem> findAllByDivisionId(Long divisionId, Pageable pageable);
    Page<VillageListItem> findAllByVillageCodeContaining(String villageCode, Pageable pageable);
    Page<VillageListItem> findAllByVillageNameContaining(String villageName, Pageable pageable);

    @Query(value = "select lg_village_Id as lgVillageId,village_code as villageCode, \n" +
            "village_name as villageName,\n" +
            "lu.UNION_NAME AS unionName,\n" +
            "lt.THANA_NAME AS upozillaName,\n" +
            "ld.DISTRICT_NAME AS districtName,\n" +
            "ld2.DIVISION_NAME AS divisionName,\n" +
            "hc.NAME AS center\n" +
            "from lg_villages lv \n" +
            "INNER JOIN LG_UNIONS lu \n" +
            "ON lv.UNION_ID = lu.UNION_ID \n" +
            "INNER JOIN LG_THANAS lt \n" +
            "ON lv.THANA_ID = lt.THANA_ID \n" +
            "inner JOIN LG_DISTRICTS ld \n" +
            "ON lv.DISTRICT_ID = ld.DISTRICT_ID \n" +
            "INNER JOIN LG_DIVISIONS ld2 \n" +
            "ON lv.DIVISION_ID = ld2.DIVISION_ID \n" +
            "LEFT JOIN HEALTH_CENTERS hc \n" +
            "ON lv.CENTER_ID = hc.ID " +
            "where lv.division_id =:divisionId AND " +
            "lv.district_id = :districtId",
            countQuery = "select count(*) from lg_villages lv where lv.division_id =:divisionId AND " +
                    "lv.district_id = :districtId", nativeQuery = true)
    Page<VillageListItem> findAllByDivisionIdAndDistrictId(@Param("divisionId") Long divisionId,
                                                           @Param("districtId") Long districtId,
                                                           Pageable pageable);

    @Query(value = "select lg_village_Id as lgVillageId,village_code as villageCode, \n" +
            "village_name as villageName,\n" +
            "lu.UNION_NAME AS unionName,\n" +
            "lt.THANA_NAME AS upozillaName,\n" +
            "ld.DISTRICT_NAME AS districtName,\n" +
            "ld2.DIVISION_NAME AS divisionName,\n" +
            "hc.NAME AS center\n" +
            "from lg_villages lv \n" +
            "INNER JOIN LG_UNIONS lu \n" +
            "ON lv.UNION_ID = lu.UNION_ID \n" +
            "INNER JOIN LG_THANAS lt \n" +
            "ON lv.THANA_ID = lt.THANA_ID \n" +
            "inner JOIN LG_DISTRICTS ld \n" +
            "ON lv.DISTRICT_ID = ld.DISTRICT_ID \n" +
            "INNER JOIN LG_DIVISIONS ld2 \n" +
            "ON lv.DIVISION_ID = ld2.DIVISION_ID \n" +
            "LEFT JOIN HEALTH_CENTERS hc \n" +
            "ON lv.CENTER_ID = hc.ID " +
            "where lv.division_id = :divisionId AND " +
            "lv.district_id = :districtId AND " +
            "lv.thana_id = :thanaId", countQuery = "select count(*) from lg_villages lv where " +
            "lv.division_id = :divisionId AND " +
            "lv.district_id = :districtId AND " +
            "lv.thana_id = :thanaId", nativeQuery = true)
    Page<VillageListItem> findAllByDivisionIdAndDistrictIdAndThanaId(@Param("divisionId") Long divisionId,
                                                                     @Param("districtId") Long districtId,
                                                                     @Param("thanaId") Long thanaId,
                                                                     Pageable pageable);

    @Query(value = "select lg_village_Id as lgVillageId,village_code as villageCode, \n" +
            "village_name as villageName,\n" +
            "lu.UNION_NAME AS unionName,\n" +
            "lt.THANA_NAME AS upozillaName,\n" +
            "ld.DISTRICT_NAME AS districtName,\n" +
            "ld2.DIVISION_NAME AS divisionName,\n" +
            "hc.NAME AS center\n" +
            "from lg_villages lv \n" +
            "INNER JOIN LG_UNIONS lu \n" +
            "ON lv.UNION_ID = lu.UNION_ID \n" +
            "INNER JOIN LG_THANAS lt \n" +
            "ON lv.THANA_ID = lt.THANA_ID \n" +
            "inner JOIN LG_DISTRICTS ld \n" +
            "ON lv.DISTRICT_ID = ld.DISTRICT_ID \n" +
            "INNER JOIN LG_DIVISIONS ld2 \n" +
            "ON lv.DIVISION_ID = ld2.DIVISION_ID \n" +
            "LEFT JOIN HEALTH_CENTERS hc \n" +
            "ON lv.CENTER_ID = hc.ID " +
            "where lv.division_id = :divisionId AND " +
            "lv.district_id = :districtId AND " +
            "lv.thana_id = :thanaId AND " +
            "lv.union_id = :unionId", countQuery = "select count(*) from lg_villages lv where" +
            "lv.division_id = :divisionId AND " +
            "lv.district_id = :districtId AND " +
            "lv.thana_id = :thanaId AND " +
            "lv.union_id = :unionId", nativeQuery = true)
    Page<VillageListItem> findAllByDivisionIdAndDistrictIdAndThanaIdAndUnionId(@Param("divisionId") Long divisionId,
                                                                               @Param("districtId") Long districtId,
                                                                               @Param("thanaId") Long thanaId,
                                                                               @Param("unionId") Long unionId,
                                                                               Pageable pageable);

    @Query(value = "select lg_village_Id as lgVillageId,village_code as villageCode, \n" +
            "village_name as villageName,\n" +
            "lu.UNION_NAME AS unionName,\n" +
            "lt.THANA_NAME AS upozillaName,\n" +
            "ld.DISTRICT_NAME AS districtName,\n" +
            "ld2.DIVISION_NAME AS divisionName,\n" +
            "hc.NAME AS center\n" +
            "from lg_villages lv \n" +
            "INNER JOIN LG_UNIONS lu \n" +
            "ON lv.UNION_ID = lu.UNION_ID \n" +
            "INNER JOIN LG_THANAS lt \n" +
            "ON lv.THANA_ID = lt.THANA_ID \n" +
            "inner JOIN LG_DISTRICTS ld \n" +
            "ON lv.DISTRICT_ID = ld.DISTRICT_ID \n" +
            "INNER JOIN LG_DIVISIONS ld2 \n" +
            "ON lv.DIVISION_ID = ld2.DIVISION_ID \n" +
            "LEFT JOIN HEALTH_CENTERS hc \n" +
            "ON lv.CENTER_ID = hc.ID ", countQuery = "select count(*) from lg_villages", nativeQuery = true)
    Page<VillageListItem> findAllVillage(Pageable pageable);
}
