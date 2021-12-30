package technology.grameen.gk.health.api.repositories.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ServiceSaleReportRepository extends ReportRepository{


    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (\n" +
            "SELECT psd.id total,\n" +
            "psd.PAYABLE_AMOUNT amount, s.name,patient_id,sc.alias scname\n" +
            "FROM patient_invoices pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "WHERE s.IS_LAB_TEST = 0 AND pi2.HEALTH_CENTER_ID IN :centers\n" +
            "AND (lower(s.code) LIKE 'card%' ) \n" +
            "AND TO_CHAR(pi2.created_at,'YYYY-MM-DD') = :fromDate\n" +
            "AND TO_CHAR(psd.created_at,'YYYY-MM-DD') = :fromDate\n" +
            "AND psd.refunded=0 \n" +
            "ORDER BY patient_id ASC) p",nativeQuery = true)
    Optional<ServiceSaleStates> getCardRegStats(@Param("centers") List<Long> centers, String fromDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (\n" +
            "SELECT psd.id total,\n" +
            "psd.PAYABLE_AMOUNT amount, s.name,patient_id,sc.alias scname\n" +
            "FROM patient_invoices pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "WHERE s.IS_LAB_TEST = 0 \n" +
            "AND (lower(s.code) LIKE 'card%' ) \n" +
            "AND TO_CHAR(pi2.created_at,'YYYY-MM-DD') = :fromDate\n" +
            "AND TO_CHAR(psd.created_at,'YYYY-MM-DD') = :fromDate\n" +
            "AND psd.refunded = 0 \n" +
            "ORDER BY patient_id ASC) p",nativeQuery = true)
    Optional<ServiceSaleStates> getCardRegStats(String fromDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (\n" +
            "SELECT psd.id total,\n" +
            "psd.PAYABLE_AMOUNT amount, s.name,patient_id,sc.alias scname\n" +
            "FROM patient_invoices pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id\n" +
            "JOIN HEALTH_CENTERS hc  ON pi2.HEALTH_CENTER_ID = hc.id\n" +
            "WHERE s.IS_LAB_TEST = 0 AND\n" +
            "pi2.HEALTH_CENTER_ID IN :centers \n" +
            "AND (lower(s.code) LIKE 'card%' ) \n" +
            "AND TO_CHAR(pi2.created_at,'YYYY-MM') = :yearMonth\n" +
            "AND TO_CHAR(psd.created_at,'YYYY-MM') = :yearMonth\n" +
            "AND psd.refunded = 0 \n" +
            "ORDER BY patient_id ASC) p",nativeQuery = true)
    Optional<ServiceSaleStates> getCardRegMonthlyStats(@Param("centers") List<Long> centers,
                                                       @Param("yearMonth") String fromDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (\n" +
            "SELECT psd.id total,\n" +
            "psd.PAYABLE_AMOUNT amount, s.name,patient_id,sc.alias scname\n" +
            "FROM patient_invoices pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id\n" +
            "JOIN HEALTH_CENTERS hc  ON pi2.HEALTH_CENTER_ID = hc.id\n" +
            "WHERE s.IS_LAB_TEST = 0 \n" +
            "AND (lower(s.code) LIKE 'card%' ) \n" +
            "AND TO_CHAR(pi2.created_at,'YYYY-MM') = :yearMonth\n" +
            "AND TO_CHAR(psd.created_at,'YYYY-MM') = :yearMonth\n" +
            "AND psd.refunded = 0 \n" +
            "ORDER BY patient_id ASC) p",nativeQuery = true)
    Optional<ServiceSaleStates> getCardRegMonthlyStats(@Param("yearMonth") String fromDate);

    @Query(value = "SELECT count(id) total, nvl(sum(PAYABLE_AMOUNT),0) amount FROM (" +
            "SELECT psd.id, psd.PAYABLE_AMOUNT, pi2.INVOICE_NUMBER, psd.CREATED_AT \n" +
            "FROM PATIENT_INVOICES pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "JOIN SERVICE_CATEGORIES sc ON sc.ID  = s.SERVICE_CATEGORY_ID \n" +
            "WHERE sc.alias LIKE '%accessories%' \n" +
            "AND TO_CHAR(psd.CREATED_AT,'YYYY-MM-DD') = :fromDate\n" +
            "AND psd.refunded = 0 \n" +
            ") p ", nativeQuery = true)
    Optional<ServiceSaleStates> getOtherIncomeStats(@Param("fromDate") String fromDate);

    @Query(value = "SELECT count(id) total, nvl(sum(PAYABLE_AMOUNT),0) amount FROM (" +
            "SELECT psd.id, psd.PAYABLE_AMOUNT, pi2.INVOICE_NUMBER, psd.CREATED_AT \n" +
            "FROM PATIENT_INVOICES pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "JOIN SERVICE_CATEGORIES sc ON sc.ID  = s.SERVICE_CATEGORY_ID \n" +
            "WHERE sc.alias LIKE '%accessories%' AND pi2.HEALTH_CENTER_ID IN :centers\n" +
            "AND TO_CHAR(psd.CREATED_AT,'YYYY-MM-DD') = :fromDate \n" +
            "AND psd.refunded = 0 ) p ",nativeQuery = true)
    Optional<ServiceSaleStates> getOtherIncomeStats(@Param("centers") List<Long> centers,
                                                    @Param("fromDate") String fromDate);

    @Query(value = "SELECT count(id) total, nvl(sum(PAYABLE_AMOUNT),0) amount FROM (" +
            "SELECT psd.id, psd.PAYABLE_AMOUNT, pi2.INVOICE_NUMBER, psd.CREATED_AT \n" +
            "FROM PATIENT_INVOICES pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "JOIN SERVICE_CATEGORIES sc ON sc.ID  = s.SERVICE_CATEGORY_ID \n" +
            "WHERE sc.alias LIKE '%accessories%' \n" +
            "AND TO_CHAR(psd.CREATED_AT,'YYYY-MM') = :yearMonth \n" +
            "AND psd.refunded = 0 ) p",nativeQuery = true)
    Optional<ServiceSaleStates> getOtherIncomeMonthlyStats(@Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(id) total, nvl(sum(PAYABLE_AMOUNT),0) amount FROM (" +
            "SELECT psd.id, psd.PAYABLE_AMOUNT, pi2.INVOICE_NUMBER, psd.CREATED_AT \n" +
            "FROM PATIENT_INVOICES pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "JOIN SERVICE_CATEGORIES sc ON sc.ID  = s.SERVICE_CATEGORY_ID \n" +
            "WHERE sc.alias LIKE '%accessories%' AND pi2.HEALTH_CENTER_ID IN :centers\n" +
            "AND TO_CHAR(psd.CREATED_AT,'YYYY-MM') = :yearMonth \n" +
            "AND psd.refunded = 0 ) p",nativeQuery = true)
    Optional<ServiceSaleStates> getOtherIncomeMonthlyStats(@Param("centers") List<Long> centers,
                                                           @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(id) total, nvl(sum(PAYABLE_AMOUNT),0) amount FROM (" +
            "SELECT psd.id, psd.PAYABLE_AMOUNT, pi2.INVOICE_NUMBER, psd.CREATED_AT \n" +
    "FROM PATIENT_INVOICES pi2 \n" +
    "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
    "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
    "JOIN SERVICE_CATEGORIES sc ON sc.ID  = s.SERVICE_CATEGORY_ID \n" +
    "WHERE sc.alias LIKE '%accessories%' \n" +
    "AND psd.CREATED_AT BETWEEN :startDate AND :endDate \n" +
    "AND psd.refunded = 0 ) p",nativeQuery = true)
    Optional<ServiceSaleStates> getOtherIncomeStats(@Param("startDate") LocalDateTime fromDateLDT,
                                                    @Param("endDate") LocalDateTime toDateLDT);

    @Query(value = "SELECT count(id) total, nvl(sum(PAYABLE_AMOUNT),0) amount FROM (" +
            "SELECT psd.id, psd.PAYABLE_AMOUNT, pi2.INVOICE_NUMBER, psd.CREATED_AT \n" +
            "FROM PATIENT_INVOICES pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "JOIN SERVICE_CATEGORIES sc ON sc.ID  = s.SERVICE_CATEGORY_ID \n" +
            "WHERE sc.alias LIKE '%accessories%' AND pi2.HEALTH_CENTER_ID IN :centers \n" +
            "AND psd.CREATED_AT BETWEEN :startDate AND :endDate \n" +
            "AND psd.refunded = 0 ) p",nativeQuery = true)
    Optional<ServiceSaleStates> getOtherIncomeStats(@Param("centers") List<Long> centers,
                                                    @Param("startDate") LocalDateTime fromDateLDT,
                                                    @Param("endDate") LocalDateTime toDateLDT);

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
            "             AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') = :fromDate\n" +
            "   AND TO_CHAR(psd.CREATED_AT,'YYYY-MM-DD') = :fromDate AND psd.refunded = 0 \n" +
            ") p",nativeQuery = true)
    Optional<ServiceSaleStates> getPrescriptionStats(@Param("fromDate") String fromDateLDT);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount From (SELECT psd.id total, s.name,\n" +
            "psd.PAYABLE_AMOUNT amount\n" +
            "            FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd \n" +
            "            ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND lower(s.name) LIKE '%prescription%'\n" +
            "             AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') = :fromDate\n" +
            " AND TO_CHAR(psd.CREATED_AT,'YYYY-MM-DD') = :fromDate AND psd.refunded = 0 \n" +
            ") p",nativeQuery = true)
    Optional<ServiceSaleStates> getPrescriptionStats(@Param("centers") List<Long> centers,
                                                 @Param("fromDate") String fromDateLDT);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount From (SELECT psd.id total, s.name,\n" +
            "psd.PAYABLE_AMOUNT amount\n" +
            "            FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd \n" +
            "            ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            WHERE lower(s.name) LIKE '%prescription%'\n" +
            "             AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') = :yearMonth\n" +
            "  AND TO_CHAR(psd.CREATED_AT,'YYYY-MM') = :yearMonth \n AND psd.refunded = 0 " +
            ") p",nativeQuery = true)
    Optional<ServiceSaleStates> getPrescriptionMonthlyStats(@Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount From (SELECT psd.id total, s.name,\n" +
            "psd.PAYABLE_AMOUNT amount\n" +
            "            FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd \n" +
            "            ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND lower(s.name) LIKE '%prescription%'\n" +
            "             AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') = :yearMonth\n" +
            " AND TO_CHAR(psd.CREATED_AT,'YYYY-MM') = :yearMonth\n AND psd.refunded = 0 " +
            ") p",nativeQuery = true)
    Optional<ServiceSaleStates> getPrescriptionMonthlyStats(@Param("centers") List<Long> centers,
                                                        @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount From (SELECT psd.id total, s.name,\n" +
            "psd.PAYABLE_AMOUNT amount\n" +
            " FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd \n" +
            " ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            " JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            " WHERE s.IS_LAB_TEST = 0 AND lower(s.code) LIKE '%prescription%'\n" +
            " AND psd.CREATED_AT BETWEEN :fromDate AND :toDate AND psd.refunded = 0 ) p",nativeQuery = true)
    Optional<ServiceSaleStates> getPrescriptionStats(@Param("fromDate") LocalDateTime fromDateLDT,
                                                 @Param("toDate") LocalDateTime toDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount From (SELECT psd.id total, s.name,\n" +
            "psd.PAYABLE_AMOUNT amount\n" +
            " FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd \n" +
            " ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            " JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            " WHERE s.IS_LAB_TEST=0 AND pi2.HEALTH_CENTER_ID IN :centers AND lower(s.code) LIKE '%prescription%'\n" +
            " AND psd.CREATED_AT BETWEEN :fromDate AND :toDate AND psd.refunded = 0 ) p",nativeQuery = true)
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
            "            AND (sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL) " +
            "            AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM-DD') = :fromDate\n" +
            "            AND psd.refunded = 0 " +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p", nativeQuery = true)
    Optional<ServiceSaleStates> getLabTestStats(@Param("fromDate") String fromDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND (sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL) " +
            "            AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM-DD') = :fromDate\n" +
            "            AND psd.refunded = 0 " +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p", nativeQuery = true)
    Optional<ServiceSaleStates> getLabTestStats(@Param("centers") List<Long> centers,@Param("fromDate") String fromDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE s.IS_LAB_TEST =1 \n" +
            "            AND (sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL) " +
            "            AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM') = :yearMonth\n" +
            "            AND psd.refunded = 0 " +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p", nativeQuery = true)
    Optional<ServiceSaleStates> getLabTestMonthlyStats(@Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND (sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL) " +
            "            AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM') = :yearMonth\n" +
            "            AND psd.refunded = 0 " +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p", nativeQuery = true)
    Optional<ServiceSaleStates> getLabTestMonthlyStats(@Param("centers") List<Long> centers, @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE s.IS_LAB_TEST =1 \n" +
            "            AND (sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL) " +
            "            AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND psd.CREATED_AT BETWEEN :fromDate AND :toDate \n" +
            "            AND psd.refunded = 0 " +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getLabTestStats(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND (sc.ALIAS NOT LIKE '%ultra%' OR sc.ALIAS IS NULL) " +
            "            AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND psd.CREATED_AT BETWEEN :fromDate AND :toDate \n" +
            "            AND psd.refunded = 0 " +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
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
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM-DD') = :fromDate\n" +
            "            AND psd.refunded = 0 " +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoStats(@Param("fromDate") String fromDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM-DD') = :fromDate\n " +
            "            AND psd.refunded = 0 " +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoStats(@Param("centers") List<Long> centers, @Param("fromDate") String fromDate);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM') = :yearMonth\n" +
            "            AND psd.refunded = 0 " +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoMonthlyStats(@Param("yearMonth") String yearMonth);


    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND TO_CHAR(psd.CREATED_AT ,'YYYY-MM') = :yearMonth \n" +
            "            AND psd.refunded = 0 " +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoMonthlyStats(@Param("centers") List<Long> centers, @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND psd.CREATED_AT BETWEEN :fromDate AND :toDate \n" +
            "            AND psd.refunded = 0 " +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoStats(@Param("fromDate") LocalDateTime fromDateLDT,
                                              @Param("toDate") LocalDateTime toDateLDT);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (\n" +
            "SELECT psd.id total,\n" +
            "psd.PAYABLE_AMOUNT amount, s.name,patient_id,sc.alias scname\n" +
            "FROM patient_invoices pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id\n" +
            "JOIN HEALTH_CENTERS hc  ON pi2.HEALTH_CENTER_ID = hc.id\n" +
            "WHERE s.IS_LAB_TEST = 0 \n"+
            "AND (lower(s.code) LIKE 'card%' ) \n" +
            "AND psd.CREATED_AT BETWEEN :fromDate AND :toDate\n" +
            "AND psd.refunded = 0 \n" +
            "ORDER BY patient_id ASC) p",nativeQuery = true)
    Optional<ServiceSaleStates> getCardRegStats(@Param("fromDate") LocalDateTime fromDateLDT,
                                                @Param("toDate") LocalDateTime toDateLDT);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (SELECT psd.id total,\n" +
            "NVL(psd.PAYABLE_AMOUNT,0) amount\n" +
            "            FROM PATIENT_SERVICE_DETAILS psd\n" +
            "            JOIN PATIENT_INVOICES pi2 ON pi2.ID  = psd.PATIENT_INVOICE_ID \n" +
            "            JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "            JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id \n" +
            "            WHERE pi2.HEALTH_CENTER_ID IN :centers AND s.IS_LAB_TEST =1 \n" +
            "            AND sc.ALIAS LIKE '%ultra%' AND lower(s.code) NOT LIKE '%prescription%'\n" +
            "            AND psd.CREATED_AT BETWEEN :fromDate AND :toDate \n" +
            "            AND psd.refunded = 0 \n" +
            "            GROUP BY psd.id,psd.PAYABLE_AMOUNT) p",nativeQuery = true)
    Optional<ServiceSaleStates> getUltraSonoStats(@Param("centers") List<Long> centers,
                                              @Param("fromDate") LocalDateTime fromDateLDT,
                                              @Param("toDate") LocalDateTime toDateLDT);

    @Query(value = "SELECT count(total) total, nvl(sum(amount),0) amount FROM (\n" +
            "SELECT psd.id total,\n" +
            "psd.PAYABLE_AMOUNT amount, s.name,patient_id,sc.alias scname\n" +
            "FROM patient_invoices pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN SERVICE_CATEGORIES sc ON s.SERVICE_CATEGORY_ID  = sc.id\n" +
            "JOIN HEALTH_CENTERS hc  ON pi2.HEALTH_CENTER_ID = hc.id\n" +
            "WHERE s.IS_LAB_TEST = 0 AND\n" +
            "pi2.HEALTH_CENTER_ID IN :centers\n" +
            "AND (lower(s.code) LIKE 'card%' ) \n" +
            "AND psd.CREATED_AT BETWEEN :fromDate AND :toDate\n" +
            "AND psd.refunded = 0 \n" +
            "ORDER BY patient_id ASC) p",nativeQuery = true)
    Optional<ServiceSaleStates> getCardRegStats(@Param("centers") List<Long> centers,
                                                  @Param("fromDate") LocalDateTime fromDateLDT,
                                                  @Param("toDate") LocalDateTime toDateLDT);


    interface AllServiceSaleStates{

            Integer getTotal();
            BigDecimal getAmount();
            String getName();
    }

    @Query(value = "SELECT count(psd.id) total, s.name, sum(psd.PAYABLE_AMOUNT) amount\n" +
            "                FROM PATIENT_INVOICES pi2 \n" +
            "                INNER JOIN PATIENT_SERVICE_DETAILS psd \n" +
            "                ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            "                INNER JOIN SERVICE s \n" +
            "                ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "                WHERE pi2.HEALTH_CENTER_ID IN :centers AND \n" +
            "                TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') BETWEEN :startDate\n" +
            "                AND :endDate GROUP BY s.name" +
            "               ORDER BY s.name ASC", nativeQuery = true)
    List<AllServiceSaleStates> getServiceSaleStatesByRange(@Param("centers") List<Long> centers,
                                                        @Param("startDate") String startDate,
                                                        @Param("endDate") String endDate);

    @Query(value = "SELECT count(psd.id) total, s.name, sum(psd.PAYABLE_AMOUNT) amount\n" +
            "                FROM PATIENT_INVOICES pi2 \n" +
            "                INNER JOIN PATIENT_SERVICE_DETAILS psd \n" +
            "                ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            "                INNER JOIN SERVICE s \n" +
            "                ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "                WHERE TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') BETWEEN :startDate\n" +
            "                AND :endDate GROUP BY s.name" +
            "               ORDER BY s.name ASC", nativeQuery = true)
    List<AllServiceSaleStates> getServiceSaleStatesByRange(@Param("startDate") String startDate,
                                                        @Param("endDate") String endDate);

    @Query(value = "SELECT count(psd.id) total, s.name, sum(psd.PAYABLE_AMOUNT) amount\n" +
            "                FROM PATIENT_INVOICES pi2 \n" +
            "                INNER JOIN PATIENT_SERVICE_DETAILS psd \n" +
            "                ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            "                INNER JOIN SERVICE s \n" +
            "                ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "                WHERE pi2.HEALTH_CENTER_ID IN :centers AND \n" +
            "                TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') = :date GROUP BY s.name" +
            "                ORDER BY s.name ASC",
    nativeQuery = true)
    List<AllServiceSaleStates> getServiceSaleStatesByDate(@Param("centers") List<Long> centers,
                                                       @Param("date") String date);

    @Query(value = "SELECT count(psd.id) total, s.name, sum(psd.PAYABLE_AMOUNT) amount\n" +
            "                FROM PATIENT_INVOICES pi2 \n" +
            "                INNER JOIN PATIENT_SERVICE_DETAILS psd \n" +
            "                ON psd.PATIENT_INVOICE_ID  = pi2.ID\n" +
            "                INNER JOIN SERVICE s \n" +
            "                ON s.SERVICE_ID = psd.SERVICE_ID \n" +
            "                WHERE TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') = :date GROUP BY s.name" +
            "                ORDER BY s.name ASC",
            nativeQuery = true)
    List<AllServiceSaleStates> getServiceSaleStatesByDate(@Param("date") String date);
}
