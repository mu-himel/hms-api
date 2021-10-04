package technology.grameen.gk.health.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.entity.PatientInvoice;
import technology.grameen.gk.health.api.entity.Prescription;
import technology.grameen.gk.health.api.projection.PrescriptionDetail;
import technology.grameen.gk.health.api.projection.PrescriptionListItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {



    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName, last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " ORDER BY pr.created_at DESC",nativeQuery = true,
    countQuery = "SELECT count(*) " +
                    " from prescriptions pr INNER JOIN patients p ON p.id=pr.prescription_patient_id")
    Page<PrescriptionListItem> findAllPrescriptions(Pageable pageable);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName, last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE upper(pr.p_number) LIKE upper('%'||:pNumber||'%') " +
            " AND upper(p.full_name) LIKE upper('%'||:fullName||'%') " +
            " AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE upper(pr.p_number) LIKE upper('%'||:pNumber||'%') " +
                    " AND upper(p.full_name) LIKE upper('%'||:fullName||'%') " +
                    " AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"
    )
    Page<PrescriptionListItem> findAllPrescriptions(@Param("pNumber") String pNumber,
                                                    @Param("fullName") String fullName,
                                                    @Param("date") String date, Pageable pageable);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, " +
            "pr.created_at as createdAt, p.id,p.full_name as fullName, last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE upper(pr.p_number) LIKE upper('%'||:pNumber||'%') " +
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE upper(pr.p_number) LIKE upper('%'||:pNumber||'%') "

    )
    Page<PrescriptionListItem> findAllPrescriptionsByPNumber(@Param("pNumber") String pNumber,
                                                     Pageable pageable);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName, last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE upper(p.full_name) LIKE upper('%'||:fullName||'%')"+
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE upper(p.full_name) LIKE upper('%'||:fullName||'%')"
    )
    Page<PrescriptionListItem> findAllPrescriptionsByFullName(
                                                    @Param("fullName") String fullName,
                                                     Pageable pageable);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName,last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"
    )
    Page<PrescriptionListItem> findAllPrescriptionsByDate(@Param("date") String date, Pageable pageable);

    @Query(value = "SELECT p from Prescription p WHERE p.id=:id")
    Optional<PrescriptionDetail> findByPatientId(@Param("id") Long id);

    Optional<PrescriptionDetail> findByPrescriptionPatientAndPatientInvoice(Patient patient, PatientInvoice invoice);

    @Query(value = "SELECT MAX(p.id) from prescriptions p WHERE p.center_id=:centerId", nativeQuery = true)
    Long getMaxId(@Param("centerId") Long centerId);

}
