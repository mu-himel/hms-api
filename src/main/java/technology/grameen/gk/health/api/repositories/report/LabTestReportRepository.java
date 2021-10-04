package technology.grameen.gk.health.api.repositories.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


public interface LabTestReportRepository extends ReportRepository{

    interface MonthlyLabTestReport{
       Integer getTotal();
       BigDecimal getAmount();
       Long getServiceId();
       String getName();
       Long getCenterId();
    }

    @Query(value = "SELECT COUNT(p.serviceId) AS total, p.serviceId, p.name, SUM(p.amount) AS amount, p.centerId FROM (\n" +
            "\tSELECT\n" +
            "\t        s.SERVICE_ID as serviceId,\n" +
            "\t        s.NAME ,\n" +
            "\t        (SELECT psd2.PAYABLE_AMOUNT FROM PATIENT_SERVICE_DETAILS psd2 WHERE psd2.id=psd.id ) amount,\n" +
            "\t        pi2.HEALTH_CENTER_ID as centerId \n" +
            "\t        , pi2.CREATED_AT \n" +
            "\t    FROM\n" +
            "\t        PATIENT_SERVICE_DETAILS psd \n" +
            "\t    JOIN\n" +
            "\t        PATIENT_INVOICES pi2 \n" +
            "\t            ON pi2.ID  = psd.PATIENT_INVOICE_ID  \n" +
            "\t    JOIN\n" +
            "\t        HEALTH_CENTERS hc \n" +
            "\t            ON pi2.HEALTH_CENTER_ID  = hc.id AND\n" +
            "\t            NVL(hc.THIRD_LEVEL ,0) = :regionCode\n" +
            "\t    JOIN\n" +
            "\t         SERVICE s \n" +
            "\t            ON s.SERVICE_ID = psd.SERVICE_ID AND s.IS_LAB_TEST = 1\n" +
            "\t    WHERE\n" +
            "\t        TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%' \n" +
            ") p GROUP BY p.name,p.serviceId,p.centerId", nativeQuery = true)
    List<MonthlyLabTestReport> getMonthlyLabTestReport(@Param("regionCode") String regionCode,
                                                       @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT COUNT(p.serviceId) AS total, p.serviceId, p.name, SUM(p.amount) AS amount, p.centerId FROM (\n" +
            "\tSELECT\n" +
            "\t        s.SERVICE_ID as serviceId,\n" +
            "\t        s.NAME ,\n" +
            "\t        (SELECT psd2.PAYABLE_AMOUNT FROM PATIENT_SERVICE_DETAILS psd2 WHERE psd2.id=psd.id ) amount,\n" +
            "\t        pi2.HEALTH_CENTER_ID as centerId \n" +
            "\t        , pi2.CREATED_AT \n" +
            "\t    FROM\n" +
            "\t        PATIENT_SERVICE_DETAILS psd \n" +
            "\t    JOIN\n" +
            "\t        PATIENT_INVOICES pi2 \n" +
            "\t            ON pi2.ID  = psd.PATIENT_INVOICE_ID  \n" +
            "\t    JOIN\n" +
            "\t        HEALTH_CENTERS hc \n" +
            "\t            ON pi2.HEALTH_CENTER_ID  = hc.id AND\n" +
            "\t            NVL(hc.THIRD_LEVEL ,0) = :regionCode\n" +
            "\t    JOIN\n" +
            "\t         SERVICE s \n" +
            "\t            ON s.SERVICE_ID = psd.SERVICE_ID AND s.IS_LAB_TEST = 1\n" +
            "\t    WHERE\n" +
            "\t        TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') LIKE :date||'%' \n" +
            ") p GROUP BY p.name,p.serviceId,p.centerId", nativeQuery = true)
    List<MonthlyLabTestReport> getDailyLabTestReport(@Param("regionCode") String regionCode,
                                                       @Param("date") String date);

    @Query(value = "SELECT COUNT(p.serviceId) AS total, p.serviceId, p.name, SUM(p.amount) AS amount, p.centerId FROM (\n" +
            "\tSELECT\n" +
            "\t        s.SERVICE_ID as serviceId,\n" +
            "\t        s.NAME ,\n" +
            "\t        (SELECT psd2.PAYABLE_AMOUNT FROM PATIENT_SERVICE_DETAILS psd2 WHERE psd2.id=psd.id ) amount,\n" +
            "\t        pi2.HEALTH_CENTER_ID as centerId \n" +
            "\t        , pi2.CREATED_AT \n" +
            "\t    FROM\n" +
            "\t        PATIENT_SERVICE_DETAILS psd \n" +
            "\t    JOIN\n" +
            "\t        PATIENT_INVOICES pi2 \n" +
            "\t            ON pi2.ID  = psd.PATIENT_INVOICE_ID  \n" +
            "\t    JOIN\n" +
            "\t        HEALTH_CENTERS hc \n" +
            "\t            ON pi2.HEALTH_CENTER_ID  = hc.id AND\n" +
            "\t            NVL(hc.CENTER_CODE ,0) = :centerCode\n" +
            "\t    JOIN\n" +
            "\t         SERVICE s \n" +
            "\t            ON s.SERVICE_ID = psd.SERVICE_ID AND s.IS_LAB_TEST = 1\n" +
            "\t    WHERE\n" +
            "\t        TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') LIKE :date||'%' \n" +
            ") p GROUP BY p.name,p.serviceId,p.centerId", nativeQuery = true)
    List<MonthlyLabTestReport> getCenterWiseDailyLabTestReport(@Param("centerCode") String centerCode,
                                                     @Param("date") String date);
}
