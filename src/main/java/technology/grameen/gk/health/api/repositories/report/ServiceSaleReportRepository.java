package technology.grameen.gk.health.api.repositories.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ServiceSaleReportRepository extends ReportRepository{



    interface ServiceSaleStates{
        Integer getTotal();
        BigDecimal getAmount();
    }

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount From (SELECT psd.id total, s.name,\n" +
            "psd.PAYABLE_AMOUNT amount\n" +
            "            FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd \n" +
            "            ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            WHERE lower(s.name) LIKE '%prescription%'\n" +
            "             AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') = :fromDate) p",nativeQuery = true)
    Optional<ServiceSaleStates> getPrescriptionStats(@Param("fromDate") String fromDateLDT);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount From (SELECT psd.id total, s.name,\n" +
            "psd.PAYABLE_AMOUNT amount\n" +
            "            FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd \n" +
            "            ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND lower(s.name) LIKE '%prescription%'\n" +
            "             AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') = :fromDate) p",nativeQuery = true)
    Optional<ServiceSaleStates> getPrescriptionStats(@Param("centers") List<Long> centers,
                                                 @Param("fromDate") String fromDateLDT);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount From (SELECT psd.id total, s.name,\n" +
            "psd.PAYABLE_AMOUNT amount\n" +
            "            FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd \n" +
            "            ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            WHERE lower(s.name) LIKE '%prescription%'\n" +
            "             AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') = :yearMonth) p",nativeQuery = true)
    Optional<ServiceSaleStates> getPrescriptionMonthlyStats(@Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount From (SELECT psd.id total, s.name,\n" +
            "psd.PAYABLE_AMOUNT amount\n" +
            "            FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd \n" +
            "            ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND lower(s.name) LIKE '%prescription%'\n" +
            "             AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') = :yearMonth) p",nativeQuery = true)
    Optional<ServiceSaleStates> getPrescriptionMonthlyStats(@Param("centers") List<Long> centers,
                                                        @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount From (SELECT psd.id total, s.name,\n" +
            "psd.PAYABLE_AMOUNT amount\n" +
            " FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd \n" +
            " ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            " JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            " WHERE lower(s.name) LIKE '%prescription%'\n" +
            " AND pi2.CREATED_AT BETWEEN :fromDate AND :toDate ) p",nativeQuery = true)
    Optional<ServiceSaleStates> getPrescriptionStats(@Param("fromDate") LocalDateTime fromDateLDT,
                                                 @Param("toDate") LocalDateTime toDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount From (SELECT psd.id total, s.name,\n" +
            "psd.PAYABLE_AMOUNT amount\n" +
            " FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd \n" +
            " ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            " JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            " WHERE pi2.HEALTH_CENTER_ID IN :centers AND lower(s.name) LIKE '%prescription%'\n" +
            " AND pi2.CREATED_AT BETWEEN :fromDate AND :toDate ) p",nativeQuery = true)
    Optional<ServiceSaleStates> getPrescriptionStats(@Param("centers") List<Long> centers, @Param("fromDate")
                                                LocalDateTime fromDateLDT,
                                                 @Param("toDate") LocalDateTime toDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM-DD') = :fromDate\n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p", nativeQuery = true)
    Optional<ServiceSaleStates> getLabTestStats(@Param("fromDate") String fromDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM-DD') = :fromDate\n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p", nativeQuery = true)
    Optional<ServiceSaleStates> getLabTestStats(@Param("centers") List<Long> centers,@Param("fromDate") String fromDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM') = :yearMonth\n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p", nativeQuery = true)
    Optional<ServiceSaleStates> getLabTestMonthlyStats(@Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM') = :yearMonth\n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p", nativeQuery = true)
    Optional<ServiceSaleStates> getLabTestMonthlyStats(@Param("centers") List<Long> centers, @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND psd.CREATED_AT BETWEEN :fromDate AND :toDate \n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getLabTestStats(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND psd.CREATED_AT BETWEEN :fromDate AND :toDate \n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getLabTestStats(@Param("centers") List<Long> centers,
                                            @Param("fromDate") LocalDateTime fromDate,
                                            @Param("toDate") LocalDateTime toDate);


    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM-DD') = :fromDate\n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoStats(@Param("fromDate") String fromDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM-DD') = :fromDate\n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoStats(@Param("centers") List<Long> centers, @Param("fromDate") String fromDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM') = :yearMonth\n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoMonthlyStats(@Param("yearMonth") String yearMonth);


    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM') = :yearMonth \n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoMonthlyStats(@Param("centers") List<Long> centers, @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND psd.CREATED_AT BETWEEN :fromDate AND :toDate \n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoStats(@Param("fromDate") LocalDateTime fromDateLDT,
                                              @Param("toDate") LocalDateTime toDateLDT);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.name) NOT LIKE '%prescription%'\n" +
            "             AND psd.CREATED_AT BETWEEN :fromDate AND :toDate \n" +
            "             GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoStats(@Param("centers") List<Long> centers,
                                              @Param("fromDate") LocalDateTime fromDateLDT,
                                              @Param("toDate") LocalDateTime toDateLDT);
}
