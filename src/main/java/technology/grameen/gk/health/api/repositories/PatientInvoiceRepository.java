package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.projection.MonthWiseReceived;
import technology.grameen.gk.health.api.projection.PatientInvoiceDetail;
import technology.grameen.gk.health.api.entity.PatientInvoice;
import technology.grameen.gk.health.api.projection.PatientInvoiceAutoComplete;
import technology.grameen.gk.health.api.projection.PrescriptionInvoiceAutoComplete;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientInvoiceRepository extends JpaRepository<PatientInvoice,Long> {

    @Query(value = "SELECT Max(pi.id) FROM PatientInvoice pi")
    Integer getMaxInvoiceId();


    @Query(value = "SELECT pi FROM PatientInvoice pi JOIN FETCH pi.patient p " +
            " LEFT JOIN FETCH pi.patientServiceDetails psd " +
            " LEFT JOIN FETCH psd.service s JOIN FETCH s.serviceCategory sc" +
            " JOIN FETCH p.center pc " +
            " LEFT JOIN FETCH p.detail d" +
            " LEFT JOIN FETCH p.registration r " +
            " LEFT JOIN FETCH r.members m " +
            " LEFT JOIN FETCH p.prescriptions ps" +
            " LEFT JOIN FETCH s.labTestAttributes lta" +
            " LEFT JOIN FETCH lta.labTestUnit ltu" +
            " LEFT JOIN FETCH p.createdBy createdBy where pi.id = :number")
    Optional<PatientInvoiceDetail> findByInvoiceId(@Param("number") Long number);

    List<PatientInvoiceAutoComplete> findByInvoiceNumberContaining(String number);

    @Query(value = "SELECT SUM (PAID_AMOUNT) FROM patient_invoices " +
            "WHERE CREATED_AT BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS')",nativeQuery = true)
    Optional<BigDecimal> getTotalAmount(@Param("fromDate") String fromDate, @Param("toDate") String  toDate);

    @Query(value = "SELECT SUM (PAID_AMOUNT) FROM patient_invoices " +
            "WHERE CREATED_AT <  " +
            " TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS')",nativeQuery = true)
    Optional<BigDecimal> getTotalAmountUptoLastDay(@Param("toDate") String  toDate);

    @Query(value ="SELECT SUM (PAID_AMOUNT) FROM patient_invoices " +
            "WHERE CREATED_AT BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND HEALTH_CENTER_ID IN :centerIds",nativeQuery = true)
    Optional<BigDecimal> getTotalAmountByCenterIds(@Param("centerIds") List<Long> centerIds,
                                         @Param("fromDate") String fromDate,
                                         @Param("toDate") String toDate);

    @Query(value ="SELECT SUM (PAID_AMOUNT) FROM patient_invoices " +
            "WHERE CREATED_AT < " +
            " TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND HEALTH_CENTER_ID IN :centerIds",nativeQuery = true)
    Optional<BigDecimal> getTotalAmountByCenterIdsUpToLastDay(@Param("centerIds") List<Long> centerIds,
                                                   @Param("toDate") String toDate);

    @Query(value = "SELECT \n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-01')) AS \"Jan\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-02')) AS \"Feb\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-03')) AS \"Mar\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-04')) AS \"Apr\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-05')) AS \"May\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-06')) AS \"Jun\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-07')) AS \"Jul\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-08')) AS \"Aug\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-09')) AS \"Sep\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-10')) AS \"Oct\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-11')) AS \"Nov\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-12')) AS \"Dec\"\n" +
            "FROM DUAL",nativeQuery = true)
    MonthWiseReceived getTotalAmountMonthWise();

    @Query(value = "SELECT \n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-01')) AS \"Jan\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-02')) AS \"Feb\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-03')) AS \"Mar\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-04')) AS \"Apr\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-05')) AS \"May\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-06')) AS \"Jun\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-07')) AS \"Jul\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-08')) AS \"Aug\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-09')) AS \"Sep\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-10')) AS \"Oct\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-11')) AS \"Nov\",\n" +
            " (SELECT COALESCE(SUM (PAID_AMOUNT),0) \"Jan\" FROM patient_invoices WHERE HEALTH_CENTER_ID IN :centerIds AND TO_CHAR(CREATED_AT,'YYYY-MM') = CONCAT(to_char(sysdate, 'YYYY'),'-12')) AS \"Dec\"\n" +
            "FROM DUAL",nativeQuery = true)
    MonthWiseReceived getTotalAmountMonthWiseInCenters(@Param("centerIds") List<Long> centerIds);


    @Query(value = "SELECT pi2.ID, pi2.INVOICE_NUMBER as invoiceNumber, p.full_name as patientFullName, p.pid as pid, " +
            "pi2.invoice_type as invoiceType, pi2.event_id as eventId FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd " +
            "ON pi2.ID  = psd.PATIENT_INVOICE_ID " +
            "JOIN PATIENTS p ON p.ID = pi2.PATIENT_ID " +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID " +
            "WHERE (upper(s.NAME) LIKE upper('%prescription%') OR upper(s.name) LIKE upper('%doctor%')) " +
            "AND psd.IS_REPORT_GENERATED = 0 AND pi2.health_center_id=:centerId " +
            "GROUP BY pi2.ID,pi2.INVOICE_NUMBER,p.full_name,p.pid,pi2.invoice_type,pi2.event_id "+
            " ORDER BY pi2.id ASC ", nativeQuery = true)
    List<PrescriptionInvoiceAutoComplete> getPrescriptionInvoiceNumbers(@Param("centerId") Integer centerId);

    List<PatientInvoiceAutoComplete> findByInvoiceNumberContainingIgnoreCase(String number);

    @Query(value = "SELECT sum(pi2.PAID_AMOUNT) AS totalPaid FROM  PATIENT_INVOICES pi2 WHERE pi2.IS_POSTED=0", nativeQuery = true)
    Optional<BigDecimal> getTotalUnPostedAmount();

    @Modifying
    @Query(value = "UPDATE PATIENT_INVOICES SET IS_POSTED=1 WHERE IS_POSTED = 0 OR IS_POSTED IS NULL",nativeQuery = true)
    Integer postInvoice();

    @Query(value = "SELECT pi2.ID, pi2.INVOICE_NUMBER as invoiceNumber, p.full_name as patientFullName, p.pid as pid " +
            "FROM PATIENT_INVOICES pi2 JOIN PATIENT_SERVICE_DETAILS psd " +
            "ON pi2.ID  = psd.PATIENT_INVOICE_ID " +
            "JOIN PATIENTS p ON p.ID = pi2.PATIENT_ID " +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID " +
            "WHERE s.IS_LAB_TEST = 1 AND pi2.health_center_id=:centerId " +
            "AND psd.IS_REPORT_GENERATED = 0 " +
            "GROUP BY pi2.ID,pi2.INVOICE_NUMBER,p.full_name,p.pid "+
            "ORDER BY pi2.id ASC ", nativeQuery = true)
    List<PrescriptionInvoiceAutoComplete> getLabTestInvoiceNumbers(@Param("centerId") Integer centerId);
}
