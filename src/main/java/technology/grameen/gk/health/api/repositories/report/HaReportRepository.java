package technology.grameen.gk.health.api.repositories.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HaReportRepository extends ReportRepository{

    interface HomeVisitCount{
        Long getTotal();
        String getCenterCode();
        Long getCenterId();
    }

    @Query(value = "SELECT count(p.homeVisitCount) total, p.center_code centerCode, p.center_id centerId FROM (\n" +
            "SELECT COUNT(*) homeVisitCount ,center_id,center_code FROM (" +
            "SELECT hc.CENTER_CODE, hc.id center_id, hpvl.HOME_ID FROM HA_PATIENT_VISIT_LOGS hpvl \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID  = hpvl.CENTER_ID\n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode\n" +
            " AND TO_CHAR(hpvl.CREATED_AT,'YYYY-MM') = :yearMonth\n" +
            "GROUP BY hc.CENTER_CODE , hc.ID, hpvl.HOME_ID\n" +
            "UNION ALL \n" +
            "SELECT hc.CENTER_CODE , hc.id, hps.HOME_ID FROM HA_PATIENT_SERVICES hps \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID = hps.CENTER_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0) = :regionCode\n" +
            "AND TO_CHAR(hps.CREATED_AT,'YYYY-MM') = :yearMonth\n" +
            "GROUP BY hc.CENTER_CODE , hc.ID, hps.HOME_ID) f GROUP BY f.CENTER_CODE,f.CENTER_ID, f.HOME_ID\n" +
            ")p GROUP BY p.center_id,p.center_code",
    nativeQuery = true)
    List<HomeVisitCount> getMonthWiseHomeVisitCount(@Param("yearMonth") String yearMonth,
                                           @Param("regionCode") String regionCode);

    @Query(value = "SELECT count(*)  total, hc.CENTER_CODE centerCode, hc.id centerId " +
            "FROM HA_PATIENT_SERVICES hps  \n" +
            "JOIN SERVICE s ON s.SERVICE_ID = hps.SERVICE_SERVICE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID  = hps.CENTER_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode\n" +
            "AND lower(s.CODE) LIKE '%diabetic%'\n" +
            "AND TO_CHAR(hps.CREATED_AT,'YYYY-MM') = :yearMonth\n" +
            "GROUP BY hc.CENTER_CODE , hc.ID",
    nativeQuery = true)
    List<HomeVisitCount> getMonthWiseDiabeticVisitCount(@Param("yearMonth") String yearMonth,
                                                    @Param("regionCode") String regionCode);

    @Query(value = "SELECT count(*) total, hc.CENTER_CODE centerCode, hc.id centerId " +
            " FROM HA_PATIENT_VISIT_LOGS hpvl \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID  = hpvl.CENTER_ID\n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode\n" +
            " AND TO_CHAR(hpvl.CREATED_AT,'YYYY-MM') = :yearMonth\n" +
            "GROUP BY hc.CENTER_CODE , hc.ID",
            nativeQuery = true
    )
    List<HomeVisitCount> getMonthWisePersonCheckupCount(@Param("yearMonth") String yearMonth,
                                                @Param("regionCode") String regionCode);

}
