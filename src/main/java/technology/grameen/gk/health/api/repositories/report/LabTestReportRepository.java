package technology.grameen.gk.health.api.repositories.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface LabTestReportRepository extends ReportRepository{

    interface MonthlyLabTestReport{
       Integer getTotal();
       Long getServiceId();
       String getName();
       Long getCenterId();
    }

    @Query(value = "SELECT count(s.SERVICE_ID) AS total,s.SERVICE_ID as serviceId,s.NAME ,pi2.HEALTH_CENTER_ID as centerId FROM PATIENT_SERVICE_DETAILS psd\n" +
            "JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID  = hc.id\n" +
            "JOIN (\n" +
            "SELECT * FROM SERVICE s WHERE s.IS_LAB_TEST = 1 ) s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0) = :regionCode AND " +
            "TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%'\n" +
            "GROUP BY s.SERVICE_ID, s.NAME, pi2.HEALTH_CENTER_ID \n" +
            "ORDER BY pi2.HEALTH_CENTER_ID ", nativeQuery = true)
    List<MonthlyLabTestReport> getMonthlyLabTestReport(@Param("regionCode") String regionCode,
                                                       @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(s.SERVICE_ID) AS total,s.SERVICE_ID as serviceId,s.NAME ,pi2.HEALTH_CENTER_ID as centerId FROM PATIENT_SERVICE_DETAILS psd\n" +
            "JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID  = hc.id\n" +
            "JOIN (\n" +
            "SELECT * FROM SERVICE s WHERE s.IS_LAB_TEST = 1 ) s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0) = :regionCode AND " +
            "TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') LIKE :date||'%'\n" +
            "GROUP BY s.SERVICE_ID, s.NAME, pi2.HEALTH_CENTER_ID \n" +
            "ORDER BY pi2.HEALTH_CENTER_ID ", nativeQuery = true)
    List<MonthlyLabTestReport> getDailyLabTestReport(@Param("regionCode") String regionCode,
                                                       @Param("date") String date);

    @Query(value = "SELECT count(s.SERVICE_ID) AS total,s.SERVICE_ID as serviceId,s.NAME ,pi2.HEALTH_CENTER_ID as centerId FROM PATIENT_SERVICE_DETAILS psd\n" +
            "JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID  = hc.id\n" +
            "JOIN (\n" +
            "SELECT * FROM SERVICE s WHERE s.IS_LAB_TEST = 1 ) s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "WHERE NVL(hc.CENTER_CODE,0) = :centerCode AND " +
            "TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') LIKE :date||'%'\n" +
            "GROUP BY s.SERVICE_ID, s.NAME, pi2.HEALTH_CENTER_ID \n" +
            "ORDER BY pi2.HEALTH_CENTER_ID ", nativeQuery = true)
    List<MonthlyLabTestReport> getCenterWiseDailyLabTestReport(@Param("centerCode") String centerCode,
                                                     @Param("date") String date);
}
