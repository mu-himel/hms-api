package technology.grameen.gk.health.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.LabTest;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.entity.PatientInvoice;
import technology.grameen.gk.health.api.entity.Service;
import technology.grameen.gk.health.api.projection.LabTestDetailItem;
import technology.grameen.gk.health.api.projection.LabTestListItem;


import java.util.List;
import java.util.Optional;


@Repository
public interface LabTestRepository extends JpaRepository<LabTest,Long> {

    @Query(value = "SELECT lt.id, lt.status, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber," +
            " lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID " +
            "ORDER BY lt.id DESC",
            countQuery = "SELECT count(*) from LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID" +
                    " INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID", nativeQuery=true)
    Page<LabTestListItem> getLabTests(Pageable pageable);

    @Query(value = "SELECT lt.id, lt.status, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber," +
            " lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID " +

            "WHERE upper(pi2.invoice_number) LIKE upper('%'||:invoiceNumber||'%') " +
            " AND upper(p.full_name) LIKE upper('%'||:fullName||'%')" +
            " AND upper(p.pid) LIKE upper('%'||:pid||'%')"+
            " ORDER BY lt.id DESC",
            countQuery = "SELECT count(*) from LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID" +
                    " INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID" +
                    " WHERE upper(pi2.invoice_number) LIKE upper('%'||:invoiceNumber||'%') " +
                    " AND upper(p.full_name) LIKE upper('%'||:fullName||'%')" +
                    " AND upper(p.pid) LIKE upper('%'||:pid||'%')", nativeQuery=true)
    Page<LabTestListItem> getLabTests(@Param("invoiceNumber") String invoiceId,
                                      @Param("fullName") String fullName,
                                      @Param("pid") String pid, Pageable pageable);

    @Query(value = "SELECT lt.id, lt.status, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber," +
            " lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID " +

            "WHERE upper(pi2.invoice_number) LIKE upper('%'||:invoiceNumber||'%') " +
            " ORDER BY lt.id DESC",
            countQuery = "SELECT count(*) from LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID" +
                    " INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID" +
                    " WHERE upper(pi2.invoice_number) LIKE upper('%'||:invoiceNumber||'%') " , nativeQuery=true)
    Page<LabTestListItem> getLabTestsByInvoiceNumber(@Param("invoiceNumber") String invoiceId,
                                       Pageable pageable);

    @Query(value = "SELECT lt.id, lt.status, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber," +
            " lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID " +
            "WHERE upper(p.full_name) LIKE upper('%'||:fullName||'%')" +
            " ORDER BY lt.id DESC",
            countQuery = "SELECT count(*) from LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID" +
                    " INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID" +

                    " WHERE upper(p.full_name) LIKE upper('%'||:fullName||'%')", nativeQuery=true)
    Page<LabTestListItem> getLabTestByFullName(@Param("fullName") String fullName,Pageable pageable);

    @Query(value = "SELECT lt.id, lt.status, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber," +
            " lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID " +
            "WHERE upper(p.pid) LIKE upper('%'||:pid||'%')"+
            " ORDER BY lt.id DESC",
            countQuery = "SELECT count(*) from LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID" +
                    " INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID" +
                    " WHERE upper(p.pid) LIKE upper('%'||:pid||'%')", nativeQuery=true)
    Page<LabTestListItem> getLabTestsByPid(@Param("pid") String pid, Pageable pageable);

    @Query(value = "SELECT lt.id, lt.status, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber," +
            " lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID " +
            "WHERE status=:status"+
            " ORDER BY lt.id DESC",
            countQuery = "SELECT count(*) from LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID" +
                    " INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID" +

                    " WHERE status=:status", nativeQuery=true)
    Page<LabTestListItem> findAllByStatus(@Param("status") String status, Pageable pageable);

    @Query("Select l from LabTest l where l.id = :id")
    Optional<LabTestDetailItem> findByLabTest(@Param("id") Long id);

    Optional<LabTestDetailItem> findByPatientAndPatientInvoice(Patient patient,
                                                                         PatientInvoice patientInvoice);

}
