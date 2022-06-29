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
import technology.grameen.gk.health.api.services.criteria.PrescriptionListService;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long>, PrescriptionListService {



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
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE NVL(hc.THIRD_LEVEL,'0') = :regionCode " +
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE NVL(hc.THIRD_LEVEL,'0') = :regionCode ")
    Page<PrescriptionListItem> findAllPrescriptions(@Param("regionCode") String regionCode,Pageable pageable);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName, last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode " +
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id" +
                    " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode ")
    Page<PrescriptionListItem> findAllPrescriptionsByCenter(@Param("centerCode") String centerCode,Pageable pageable);

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

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, " +
            "pr.created_at as createdAt, p.id,p.full_name as fullName, " +
            "last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE NVL(hc.THIRD_LEVEL,'0') = :regionCode " +
            " AND upper(pr.p_number) LIKE upper('%'||:pNumber||'%') " +
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE NVL(hc.THIRD_LEVEL,'0') = :regionCode " +
                    " AND upper(pr.p_number) LIKE upper('%'||:pNumber||'%') "

    )
    Page<PrescriptionListItem> findAllPrescriptionsByPNumber(@Param("regionCode") String regionCode,
                                                             @Param("pNumber") String pNumber,
                                                             Pageable pageable);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, " +
            "pr.created_at as createdAt, p.id,p.full_name as fullName, " +
            "last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode " +
            " AND upper(pr.p_number) LIKE upper('%'||:pNumber||'%') " +
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode " +
                    " AND upper(pr.p_number) LIKE upper('%'||:pNumber||'%') "

    )
    Page<PrescriptionListItem> findAllPrescriptionsByPNumberByCenter(@Param("centerCode") String regionCode,
                                                             @Param("pNumber") String pNumber,
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
            "p.id,p.full_name as fullName, last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE NVL(hc.THIRD_LEVEL,'0') = :regionCode AND upper(p.full_name) LIKE upper('%'||:fullName||'%')"+
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE NVL(hc.THIRD_LEVEL,'0') = :regionCode AND " +
                    " upper(p.full_name) LIKE upper('%'||:fullName||'%')"
    )
    Page<PrescriptionListItem> findAllPrescriptionsByFullName(@Param("regionCode") String regionCode,
            @Param("fullName") String fullName,
            Pageable pageable);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName, last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode AND upper(p.full_name) LIKE upper('%'||:fullName||'%')"+
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode AND " +
                    " upper(p.full_name) LIKE upper('%'||:fullName||'%')"
    )
    Page<PrescriptionListItem> findAllPrescriptionsByFullNameByCenter(@Param("centerCode") String regionCode,
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

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName,last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE NVL(hc.THIRD_LEVEL,'0') = :regionCode AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE NVL(hc.THIRD_LEVEL,'0') = :regionCode " +
                    " AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"
    )
    Page<PrescriptionListItem> findAllPrescriptionsByDate(@Param("regionCode") String regionCode,
                                                          @Param("date") String date,
                                                          Pageable pageable);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName,last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode " +
                    "AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"
    )
    Page<PrescriptionListItem> findAllPrescriptionsByDateByCenter(@Param("centerCode") String regionCode,
                                                          @Param("date") String date,
                                                          Pageable pageable);

    @Query(value = "SELECT p from Prescription p WHERE p.id=:id")
    Optional<PrescriptionDetail> findByPatientId(@Param("id") Long id);

    Optional<PrescriptionDetail> findByPrescriptionPatientAndPatientInvoice(Patient patient, PatientInvoice invoice);

    @Query(value = "SELECT count(p.id) from prescriptions p WHERE to_char(p.created_at,'YYYY-MM-DD') = :today AND p.center_id=:centerId", nativeQuery = true)
    Long getMaxId(@Param("centerId") Long centerId, @Param("today") String toDate);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName,last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE pr.p_number LIKE '%'||:pNumber||'%' " +
            " AND lower(p.full_name) LIKE '%'||:fullName||'%' " +
            " AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE pr.p_number LIKE '%'||:pNumber||'%' " +
                    " AND lower(p.full_name) LIKE '%'||lower(:fullName)||'%' " +
                    " AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
                    "AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"
    )
    Page<PrescriptionListItem> findAllPrescriptionsByPNumberAndFullNameAndDate(
            @Param("pNumber") String pNumber,
            @Param("fullName") String fullName,
            @Param("date") String date, Pageable pageable);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName,last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE lower(p.full_name) LIKE '%'||:fullName||'%' " +
            " AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE lower(p.full_name) LIKE '%'||lower(:fullName)||'%' " +
                    " AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
                    "AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"
    )
    Page<PrescriptionListItem> findAllPrescriptionsByFullNameAndDate(String fullName, String date, Pageable pageable);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName,last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode " +
            " AND pr.p_number LIKE '%'||:pNumber||'%' " +
            " AND lower(p.full_name) LIKE '%'||:fullName||'%' " +
            " AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode " +
                    " AND pr.p_number LIKE '%'||:pNumber||'%' " +
                    " AND lower(p.full_name) LIKE '%'||lower(:fullName)||'%' " +
                    " AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
                    "AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"
    )
    Page<PrescriptionListItem> findAllPrescriptionsByPNumberAndFullNameAndDate(
            @Param("centerCode")String centerCode,
            @Param("pNumber") String pNumber,
            @Param("fullName") String fullName,
            @Param("date") String date, Pageable pageable);

    @Query(value = "SELECT pr.id as prescriptionId, pr.p_number as pNumber, pr.created_at as createdAt, " +
            "p.id,p.full_name as fullName,last_free_visit_date as lastFreeVisitDate " +
            " from prescriptions pr" +
            " JOIN health_centers hc ON pr.center_id = hc.id" +
            " INNER JOIN patients p ON p.id = pr.prescription_patient_id" +
            " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode " +
            " AND lower(p.full_name) LIKE '%'||:fullName||'%' " +
            " AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
            " ORDER BY pr.created_at DESC",nativeQuery = true,
            countQuery = "SELECT count(*) " +
                    " from prescriptions pr " +
                    " JOIN health_centers hc ON pr.center_id = hc.id" +
                    " INNER JOIN patients p ON p.id=pr.prescription_patient_id"+
                    " WHERE NVL(hc.CENTER_CODE,'0') = :centerCode " +
                    " AND lower(p.full_name) LIKE '%'||lower(:fullName)||'%' " +
                    " AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"+
                    "AND TO_CHAR(pr.created_at,'YYYY-MM-DD') = :date"
    )
    Page<PrescriptionListItem> findAllPrescriptionsByFullNameAndDate(
            @Param("centerCode") String centerCode,
            @Param("fullName") String fullName,
            @Param("date") String date, Pageable pageable);
}
