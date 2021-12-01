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

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM(SELECT lt.id,s.NAME,lt.status, p.FULL_NAME AS fullName , p.PID as pid ,\n" +
            "\tpi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "    lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "    INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "    INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
            "    INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "    INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "    JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "    GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "    ORDER BY lt.id DESC) r\n" +
            "    GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "select count(*) from(SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM(SELECT lt.id,s.NAME,lt.status, p.FULL_NAME AS fullName , p.PID as pid ,\n" +
                    "\tpi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "    lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "    INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
                    "    INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
                    "    INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "    INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "    JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "    GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "    ORDER BY lt.id DESC) r\n" +
                    "    GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> getLabTests(Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM(SELECT lt.id,s.NAME,lt.status, p.FULL_NAME AS fullName , p.PID as pid ,\n" +
            "\tpi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "    lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "    INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "    INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
            "    INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "    INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "    JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "    WHERE lt.center_id IN :centers " +
            "    GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "    ORDER BY lt.id DESC) r\n" +
            "    GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "select count(*) from(SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM(SELECT lt.id,s.NAME,lt.status, p.FULL_NAME AS fullName , p.PID as pid ,\n" +
                    "\tpi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "    lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "    INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
                    "    INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
                    "    INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "    INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "    JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "    WHERE lt.center_id IN :centers " +
                    "    GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "    ORDER BY lt.id DESC) r\n" +
                    "    GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> getLabTests(@Param("centers") List<Long> centerIds, Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM(SELECT lt.id,s.NAME,lt.status, p.FULL_NAME AS fullName , p.PID as pid ,\n" +
            "\tpi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "    lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "    INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "    INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
            "    INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "    INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "    JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "    WHERE lt.center_id = :centerId " +
            "    GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "    ORDER BY lt.id DESC) r\n" +
            "    GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "select count(*) from(SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM(SELECT lt.id,s.NAME,lt.status, p.FULL_NAME AS fullName , p.PID as pid ,\n" +
                    "\tpi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "    lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "    INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
                    "    INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
                    "    INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "    INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "    JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "    WHERE lt.center_id = :centerId " +
                    "    GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "    ORDER BY lt.id DESC) r\n" +
                    "    GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> getLabTests(@Param("centerId") Long centerId, Pageable pageable);

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

            "WHERE lt.center_id in :centers AND upper(pi2.invoice_number) LIKE upper('%'||:invoiceNumber||'%') " +
            " AND upper(p.full_name) LIKE upper('%'||:fullName||'%')" +
            " AND upper(p.pid) LIKE upper('%'||:pid||'%')"+
            " ORDER BY lt.id DESC",
            countQuery = "SELECT count(*) from LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID" +
                    " INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID" +
                    " WHERE lt.center_id in :centers AND upper(pi2.invoice_number) LIKE upper('%'||:invoiceNumber||'%') " +
                    " AND upper(p.full_name) LIKE upper('%'||:fullName||'%')" +
                    " AND upper(p.pid) LIKE upper('%'||:pid||'%')", nativeQuery=true)
    Page<LabTestListItem> getLabTests(@Param("centers") List<Long> centerIds,
                                      @Param("invoiceNumber") String invoiceId,
                                      @Param("fullName") String fullName,
                                      @Param("pid") String pid, Pageable pageable);


    @Query(value = "SELECT lt.id, lt.status, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber," +
            " lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID " +

            "WHERE lt.center_id = :centerId AND upper(pi2.invoice_number) LIKE upper('%'||:invoiceNumber||'%') " +
            " AND upper(p.full_name) LIKE upper('%'||:fullName||'%')" +
            " AND upper(p.pid) LIKE upper('%'||:pid||'%')"+
            " ORDER BY lt.id DESC",
            countQuery = "SELECT count(*) from LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID" +
                    " INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID" +
                    " WHERE lt.center_id = :centerId AND upper(pi2.invoice_number) LIKE upper('%'||:invoiceNumber||'%') " +
                    " AND upper(p.full_name) LIKE upper('%'||:fullName||'%')" +
                    " AND upper(p.pid) LIKE upper('%'||:pid||'%')", nativeQuery=true)
    Page<LabTestListItem> getLabTests(@Param("centerId") Long centerId,
                                      @Param("invoiceNumber") String invoiceId,
                                      @Param("fullName") String fullName,
                                      @Param("pid") String pid, Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "WHERE upper(pi2.invoice_number) LIKE upper('%' || :invoiceNumber || '%') \n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "select count(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "WHERE upper(pi2.invoice_number) LIKE upper('%' || invoiceNumber || '%') \n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c" , nativeQuery=true)
    Page<LabTestListItem> getLabTestsByInvoiceNumber(@Param("invoiceNumber") String invoiceId,
                                       Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "WHERE lt.center_id IN :centers AND upper(pi2.invoice_number) LIKE upper('%' || :invoiceNumber || '%') \n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "select count(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "WHERE lt.center_id IN :centers AND upper(pi2.invoice_number) LIKE upper('%' || invoiceNumber || '%') \n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c" , nativeQuery=true)
    Page<LabTestListItem> getLabTestsByInvoiceNumber(@Param("centers") List<Long> centerId,@Param("invoiceNumber") String invoiceId,
                                                     Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "WHERE lt.center_id=:centerId AND upper(pi2.invoice_number) LIKE upper('%' || :invoiceNumber || '%') \n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "select count(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "WHERE lt.center_id=:centerId AND upper(pi2.invoice_number) LIKE upper('%' || invoiceNumber || '%') \n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c" , nativeQuery=true)
    Page<LabTestListItem> getLabTestsByInvoiceNumber(@Param("centerId") Long centerId,@Param("invoiceNumber") String invoiceId,
                                                     Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM( SELECT lt.id, lt.status, s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
            "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
            "WHERE upper(p.full_name) LIKE upper('%'|| :fullName ||'%')\n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC ) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "SELECT COUNT(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM( SELECT lt.id, lt.status, s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
                    "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
                    "WHERE upper(p.full_name) LIKE upper('%'|| :fullName ||'%')\n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC ) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> getLabTestByFullName(@Param("fullName") String fullName,Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM( SELECT lt.id, lt.status, s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
            "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
            "WHERE lt.center_id in :centers AND upper(p.full_name) LIKE upper('%'|| :fullName ||'%')\n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC ) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "SELECT COUNT(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM( SELECT lt.id, lt.status, s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
                    "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
                    "WHERE lt.center_id in :centers AND upper(p.full_name) LIKE upper('%'|| :fullName ||'%')\n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC ) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> getLabTestByFullName(@Param("centers")List<Long> centerId,
                                               @Param("fullName") String fullName,
                                               Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM( SELECT lt.id, lt.status, s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
            "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
            "WHERE lt.center_id = :centerId AND upper(p.full_name) LIKE upper('%'|| :fullName ||'%')\n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC ) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "SELECT COUNT(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM( SELECT lt.id, lt.status, s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
                    "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID \n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID \n" +
                    "WHERE lt.center_id = :centerId AND upper(p.full_name) LIKE upper('%'|| :fullName ||'%')\n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC ) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> getLabTestByFullName(@Param("centerId")Long centerId,
                                               @Param("fullName") String fullName,
                                               Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM(  SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName, p.PID as pid , \n" +
            "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
            "WHERE upper(p.pid) LIKE upper('%' || :pid || '%')\n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "SELECT COUNT(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM(  SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName, p.PID as pid , \n" +
                    "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
                    "WHERE upper(p.pid) LIKE upper('%' || :pid || '%')\n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> getLabTestsByPid(@Param("pid") String pid, Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM(  SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName, p.PID as pid , \n" +
            "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
            "WHERE lt.center_id in :centers AND upper(p.pid) LIKE upper('%' || :pid || '%')\n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "SELECT COUNT(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM(  SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName, p.PID as pid , \n" +
                    "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
                    "WHERE lt.center_id in :centers AND upper(p.pid) LIKE upper('%' || :pid || '%')\n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> getLabTestsByPid(@Param("centers") List<Long> centerIds,
                                           @Param("pid") String pid, Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM(  SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName, p.PID as pid , \n" +
            "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
            "WHERE lt.center_id = :centerId AND upper(p.pid) LIKE upper('%' || :pid || '%')\n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "SELECT COUNT(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM(  SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName, p.PID as pid , \n" +
                    "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
                    "WHERE lt.center_id = :centerId AND upper(p.pid) LIKE upper('%' || :pid || '%')\n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> getLabTestsByPid(@Param("centerId") Long centerId,
                                           @Param("pid") String pid, Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
            "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
            "WHERE status=:status\n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "SELECT COUNT(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
                    "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
                    "WHERE status=:status\n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> findAllByStatus(@Param("status") String status, Pageable pageable);

    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
            "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
            "WHERE lt.center_id in :centers AND status=:status\n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "SELECT COUNT(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
                    "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
                    "WHERE lt.center_id in :centers AND status=:status\n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> findAllByStatus(@Param("centers") List<Long> centerIds,
                                          @Param("status") String status,
                                          Pageable pageable);


    @Query(value = "SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
            "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
            "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
            "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
            "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
            "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
            "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
            "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
            "WHERE lt.center_id = :centerId AND status=:status\n" +
            "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
            "ORDER BY lt.id DESC) r\n" +
            "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat",
            countQuery = "SELECT COUNT(c.id) FROM (SELECT r.id,LISTAGG(r.name, ', ') servicename,r.status,r.fullname,r.pid, r.invoicenumber, r.createdat \n" +
                    "FROM( SELECT lt.id, lt.status,s.name, p.FULL_NAME AS fullName , p.PID as pid , \n" +
                    "pi2.INVOICE_NUMBER AS invoiceNumber,\n" +
                    "lt.CREATED_AT AS createdAt FROM  LAB_TESTS lt \n" +
                    "INNER JOIN PATIENTS p ON p.id = lt.PATIENT_ID\n" +
                    "INNER JOIN LAB_TESTS_SERVICES lts ON lts.lab_test_id = lt.id\n" +
                    "INNER JOIN SERVICE s ON s.service_id = lts.services_service_id\n" +
                    "INNER JOIN PATIENT_INVOICES pi2 ON pi2.id = lt.PATIENT_INVOICE_ID\n" +
                    "WHERE lt.center_id = :centerId AND status=:status\n" +
                    "GROUP BY lt.id,lt.STATUS,s.name, p.FULL_NAME , p.pid, pi2.INVOICE_NUMBER, lt.CREATED_AT\n" +
                    "ORDER BY lt.id DESC) r\n" +
                    "GROUP BY r.id,r.status,r.fullname,r.pid,r.invoicenumber,r.createdat) c", nativeQuery=true)
    Page<LabTestListItem> findAllByStatus(@Param("centerId") Long centerId,
                                          @Param("status") String status,
                                          Pageable pageable);

    @Query("Select l from LabTest l where l.id = :id")
    Optional<LabTestDetailItem> findByLabTest(@Param("id") Long id);

    Optional<LabTestDetailItem> findByPatientAndPatientInvoice(Patient patient,
                                                                         PatientInvoice patientInvoice);

}
