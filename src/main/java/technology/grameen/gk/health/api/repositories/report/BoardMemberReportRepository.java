package technology.grameen.gk.health.api.repositories.report;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface BoardMemberReportRepository extends ReportRepository {

    @Query(value = "select count(r) from HealthCenter r WHERE r.officeTypeId=5")
    Optional<Integer> totalRegion();

    @Query(value = "select count(c) from HealthCenter c WHERE c.officeTypeId=6")
    Optional<Integer> totalCenter();

    @Query(value = "SELECT count(*) FROM PATIENT_INVOICES pi2 WHERE pi2.INVOICE_TYPE = 'regular'\n" +
            "AND EVENT_ID IS NULL AND to_char(pi2.CREATED_AT,'YYYY-MM') BETWEEN :fromMonth AND :toMonth",
    nativeQuery = true)
    Optional<?> getPatientTreadByHc(@Param("fromMonth") String fromMonth,
                                    @Param("toMonth") String toMonth);

    @Query(value = "SELECT count(*) FROM PATIENT_INVOICES pi2 WHERE pi2.INVOICE_TYPE = 'satellite'\n" +
            "AND EVENT_ID IS NOT NULL AND to_char(pi2.CREATED_AT,'YYYY-MM') BETWEEN :fromMonth AND :toMonth",
            nativeQuery = true)
    Optional<?> getPatientTreadBySat(String fromMonth, String toMonth);

    @Query(value = "SELECT count(c.id) FROM(\n" +
            "SELECT  ec.id,ec.name,e.EVENT_DATE,e.CENTER_ID \n" +
            "    FROM EVENTS e\n" +
            "    JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID\n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "        WHERE   hc.OFFICE_TYPE_ID = 6 AND\n" +
            "            \tTO_CHAR(e.EVENT_DATE,'YYYY-MM') BETWEEN :fromMonth AND :toMonth\n" +
            "            \tAND e.EVENT_TYPE = 'satellite' \n" +
            "            \tGROUP BY ec.name,ec.id,e.EVENT_DATE,e.CENTER_ID) c", nativeQuery = true)
    Optional<?> getSatCampNo(@Param("fromMonth") String fromMonth,
                             @Param("toMonth") String toMonth);

    interface VariousCount{
        Long getTotal();
        String getName();
    }

    @Query(value = "SELECT count(c.id) total, name FROM(\n" +
            "SELECT  ec.id,ec.name,e.EVENT_DATE,e.CENTER_ID \n" +
            "    FROM EVENTS e\n" +
            "    JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID\n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "        WHERE   hc.OFFICE_TYPE_ID = 6 AND\n" +
            "            \tTO_CHAR(e.EVENT_DATE,'YYYY-MM') BETWEEN '2021-07' AND '2021-08'\n" +
            "            \tAND e.EVENT_TYPE = 'camp' \n" +
            "            \tGROUP BY ec.name,ec.id,e.EVENT_DATE,e.CENTER_ID) c \n" +
            "            \tGROUP BY c.name",nativeQuery = true)
    List<VariousCount> getNoOfVariousCamp(@Param("fromMonth") String fromMonth,
                                                     @Param("toMonth") String toMonth);


    @Query(value = "SELECT count(id) total , name from( \n" +
            "SELECT ec.id, ec.name,e.EVENT_DATE , \n" +
            "\t\t\tpi2.PATIENT_ID,pi2.EVENT_ID ,pi2.HEALTH_CENTER_ID CENTER_ID \n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN  PATIENT_INVOICES pi2 ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "            JOIN HEALTH_CENTERS hc ON hc.ID = pi2.HEALTH_CENTER_ID\n" +
            "            JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "            JOIN EVENTS e ON pi2.EVENT_ID  = e.ID\n" +
            "            JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "            WHERE TO_CHAR(e.EVENT_DATE,'YYYY-MM') BETWEEN :fromMonth AND :toMonth  \n" +
            "            AND pi2.INVOICE_TYPE = 'camp' AND hc.OFFICE_TYPE_ID = 6 \n" +
            "            AND nvl(psd.refunded,0) = 0 \n" +
            "            GROUP BY pi2.PATIENT_ID,ec.id,ec.name,pi2.EVENT_ID, e.EVENT_DATE  ,pi2.HEALTH_CENTER_ID) t\n" +
            "            GROUP  BY t.name\n",nativeQuery = true)
    List<VariousCount> getPatientTreatedAtVariousCamp(@Param("fromMonth") String fromMonth,
                                                     @Param("toMonth") String toMonth);


    @Query(value = "SELECT NVL(count(pi2.id),0) as total,\n" +
            "    s.NAME \n" +
            "    FROM patient_invoices pi2\n" +
            "    JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "    JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "    JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID \n" +
            "    WHERE sc.alias LIKE '%'||:serviceCategory||'%'\n" +
            "    AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') BETWEEN :fromMonth AND :toMonth\n" +
            "    GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,s.NAME\n",nativeQuery = true)
    List<VariousCount> getServiceCount(@Param("serviceCategory") String serviceCategory,
                                      @Param("fromMonth") String fromMonth,
                                      @Param("toMonth") String toMonth);


    @Query(value = "SELECT count(*) FROM (SELECT hc.CENTER_CODE, hc.id center_id, hpvl.HOME_ID FROM HA_PATIENT_VISIT_LOGS hpvl\n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID  = hpvl.CENTER_ID\n" +
            "    WHERE TO_CHAR(hpvl.CREATED_AT,'YYYY-MM')  BETWEEN :fromMonth AND :toMonth\n" +
            "    GROUP BY hc.CENTER_CODE , hc.ID, hpvl.HOME_ID) c",nativeQuery = true)
    Optional<?> getHouseVisit(@Param("fromMonth") String fromMonth,
                              @Param("toMonth") String toMonth);

    @Query(value = "SELECT count(*) total FROM HA_PATIENT_VISIT_LOGS hpvl \n" +
            "        WHERE TO_CHAR(hpvl.CREATED_AT,'YYYY-MM')  BETWEEN :fromMonth AND :toMonth",nativeQuery = true)
    Optional<?> getPatientCheckup(@Param("fromMonth") String fromMonth,
                                  @Param("toMonth") String toMonth);


    @Query(value = "SELECT count(*) total FROM PATIENT_DISEASE_PROFILES pdp\n" +
            "            JOIN DISEASE_PROFILES dp ON dp.ID = pdp.DISEASE_PROFILE_ID\n" +
            "            JOIN HEALTH_CENTERS hc ON hc.ID = pdp.CENTER_ID\n" +
            "            WHERE dp.ALIAS = 'pregnant'\n" +
            "            AND TO_CHAR(pdp.CREATED_AT,'YYYY-MM') BETWEEN :fromMonth AND :toMonth ",nativeQuery = true)
    Optional<?> getPregnantRegistered(@Param("fromMonth") String fromMonth,
                                      @Param("toMonth") String toMonth);


    @Query(value = "SELECT COUNT(pi2.id) as total\n" +
            "            FROM patient_invoices pi2\n" +
            "            JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID\n" +
            "            JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "            JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "            WHERE lower(s.NAME) LIKE 'delivery'||'%'\n" +
            "            AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') BETWEEN :fromMonth AND :toMonth",
    nativeQuery = true)
    Optional<?> getDeliveryPerformed(@Param("fromMonth") String fromMonth,
                                     @Param("toMonth") String toMonth);


    @Query(value = "SELECT count(*)  total\n" +
            "            FROM HA_PATIENT_SERVICES hps  \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = hps.SERVICE_SERVICE_ID\n" +
            "            JOIN HEALTH_CENTERS hc ON hc.ID  = hps.CENTER_ID \n" +
            "            WHERE lower(s.CODE) LIKE '%diabetic%'\n" +
            "            AND TO_CHAR(hps.CREATED_AT,'YYYY-MM')  BETWEEN :fromMonth AND :toMonth",
    nativeQuery = true)
    Optional<?> getDiabeticCheckup(@Param("fromMonth") String fromMonth,
                                   @Param("toMonth") String toMonth);
}
