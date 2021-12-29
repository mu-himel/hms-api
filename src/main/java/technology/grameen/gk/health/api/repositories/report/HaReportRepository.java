package technology.grameen.gk.health.api.repositories.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    @Query(value = "SELECT count(p.homeVisitCount) total, p.center_code centerCode, p.center_id centerId FROM (\n" +
            "SELECT COUNT(*) homeVisitCount ,center_id,center_code FROM (" +
            "SELECT hc.CENTER_CODE, hc.id center_id, hpvl.HOME_ID FROM HA_PATIENT_VISIT_LOGS hpvl \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID  = hpvl.CENTER_ID\n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode\n" +
            " AND TO_CHAR(hpvl.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt\n" +
            "GROUP BY hc.CENTER_CODE , hc.ID, hpvl.HOME_ID\n" +
            "UNION ALL \n" +
            "SELECT hc.CENTER_CODE , hc.id, hps.HOME_ID FROM HA_PATIENT_SERVICES hps \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID = hps.CENTER_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0) = :regionCode\n" +
            "AND TO_CHAR(hps.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt\n" +
            "GROUP BY hc.CENTER_CODE , hc.ID, hps.HOME_ID) f GROUP BY f.CENTER_CODE,f.CENTER_ID, f.HOME_ID\n" +
            ")p GROUP BY p.center_id,p.center_code",
            nativeQuery = true)
    List<HomeVisitCount> getMonthWiseHomeVisitCount(@Param("sdt") String sdt,
                                                    @Param("edt") String edt,
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

    @Query(value = "SELECT count(*)  total, hc.CENTER_CODE centerCode, hc.id centerId " +
            "FROM HA_PATIENT_SERVICES hps  \n" +
            "JOIN SERVICE s ON s.SERVICE_ID = hps.SERVICE_SERVICE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID  = hps.CENTER_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode\n" +
            "AND lower(s.CODE) LIKE '%diabetic%'\n" +
            "AND TO_CHAR(hps.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt\n" +
            "GROUP BY hc.CENTER_CODE , hc.ID",
            nativeQuery = true)
    List<HomeVisitCount> getMonthWiseDiabeticVisitCount(@Param("sdt") String sdt,
                                                        @Param("edt") String edt,
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


    @Query(value = "SELECT count(*) total, hc.CENTER_CODE centerCode, hc.id centerId " +
            " FROM HA_PATIENT_VISIT_LOGS hpvl \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID  = hpvl.CENTER_ID\n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode\n" +
            " AND TO_CHAR(hpvl.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt\n" +
            "GROUP BY hc.CENTER_CODE , hc.ID",
            nativeQuery = true
    )
    List<HomeVisitCount> getMonthWisePersonCheckupCount(@Param("sdt") String sdt,
                                                        @Param("edt") String edt,
                                                        @Param("regionCode") String regionCode);


    interface ChaReport{
        Long getReferCount();
        Long getHealthAssistantId();
    }

    @Query(value = "SELECT count(patient_id) referCount, Health_assistant_id healthAssistantId FROM (\n" +
            "SELECT pi2.PATIENT_ID, TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD'), psd.HEALTH_ASSISTANT_ID FROM PATIENT_INVOICES pi2 \n" +
            "INNER JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "WHERE TO_CHAR(psd.CREATED_AT,'YYYY-MM') = :month AND psd.HEALTH_ASSISTANT_ID IS NOT NULL \n" +
            "AND pi2.INVOICE_TYPE  = :type \n" +
            "GROUP BY pi2.PATIENT_ID, TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD'), psd.HEALTH_ASSISTANT_ID\n" +
            ") r GROUP BY health_assistant_id", nativeQuery = true)
    List<ChaReport> getPatientReferByMonth(@Param("month") String month, @Param("type") String type);


    @Query(value = "SELECT count(psd.HEALTH_ASSISTANT_ID) referCount, psd.HEALTH_ASSISTANT_ID healthAssistantId FROM PATIENT_INVOICES pi2 \n" +
            "INNER JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.ID " +
            "AND psd.HEALTH_ASSISTANT_ID IS NOT NULL\n" +
            "INNER JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "WHERE TO_CHAR(psd.CREATED_AT,'YYYY-MM') = :month AND s.CODE LIKE '%' || :serviceCode || '%'  \n" +
            "GROUP BY psd.HEALTH_ASSISTANT_ID", nativeQuery = true)
    List<ChaReport> getServiceCountByMonth(@Param("month") String month, @Param("serviceCode") String serviceCode);

    interface ChaReportVaccine{
        Long getReferCount();
        Long getHealthAssistantId();
        Long getServiceId();
    }

    @Query(value = "SELECT count(psd.HEALTH_ASSISTANT_ID) referCount, sc.id AS serviceId, psd.HEALTH_ASSISTANT_ID healthAssistantId " +
            "FROM PATIENT_INVOICES pi2 \n" +
            "INNER JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID AND psd.HEALTH_ASSISTANT_ID IS NOT NULL\n" +
            "INNER JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "INNER JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID \n" +
            "WHERE TO_CHAR(psd.CREATED_AT,'YYYY-MM') = :month AND sc.alias LIKE '%vaccine%'  \n" +
            "GROUP BY sc.id, psd.HEALTH_ASSISTANT_ID",nativeQuery = true)
    List<ChaReportVaccine> getVaccineCountByMonth(@Param("month") String month);

    @Query(value = "SELECT count(*) referCount, hpvl.HEALTH_ASSISTANT_ID healthAssistantId\n" +
            "            FROM HA_PATIENT_VISIT_LOGS hpvl \n" +
            "            JOIN HEALTH_CENTERS hc ON hc.ID  = hpvl.CENTER_ID\n" +
            "            WHERE TO_CHAR(hpvl.CREATED_AT,'YYYY-MM') = :month\n" +
            "            GROUP BY hpvl.HEALTH_ASSISTANT_ID ", nativeQuery = true)
    List<ChaReport> getPatientCheckupByMonth(@Param("month") String month);


    @Query(value = "SELECT count(p.homeVisitCount) referCount, p.health_assistant_id healthAssistantId FROM (\n" +
            "            SELECT COUNT(total) homeVisitCount , health_assistant_id FROM (\n" +
            "            SELECT count(*) total, hpvl.HEALTH_ASSISTANT_ID FROM HA_PATIENT_VISIT_LOGS hpvl \n" +
            "            JOIN HEALTH_CENTERS hc ON hc.ID  = hpvl.CENTER_ID\n" +
            "            WHERE TO_CHAR(hpvl.CREATED_AT,'YYYY-MM') = :month\n" +
            "            GROUP BY hpvl.HOME_ID, hpvl.HEALTH_ASSISTANT_ID \n" +
            "            UNION ALL \n" +
            "            SELECT count(*) total, hps.HEALTH_ASSISTANT_ID FROM HA_PATIENT_SERVICES hps \n" +
            "            JOIN HEALTH_CENTERS hc ON hc.ID = hps.CENTER_ID \n" +
            "            WHERE TO_CHAR(hps.CREATED_AT,'YYYY-MM') = :month\n" +
            "            GROUP BY hps.HOME_ID,hps.HEALTH_ASSISTANT_ID) f GROUP BY  f.health_assistant_id\n" +
            "            )p GROUP BY p.health_assistant_id",nativeQuery = true)
    List<ChaReport> getHomeVisitByMonth(@Param("month") String month);
}
