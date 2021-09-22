package technology.grameen.gk.health.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.projection.PatientNumberAutoComplete;
import technology.grameen.gk.health.api.projection.PatientSearchResult;
import technology.grameen.gk.health.api.responses.PatientListItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    @Query(value = "SELECT new technology.grameen.gk.health.api.responses.PatientListItem (p.id, p.pid, p.fullName, " +
            "p.gender, p.maritalStatus," +
            "p.age,p.isGB, c.name,p.guardianName,p.mobileNumber," +
            "p.createdAt, p.lastUpdatedAt) FROM Patient p LEFT JOIN p.center c",
            countQuery = "SELECT count(p) FROM Patient p LEFT JOIN p.center c")
    Page<PatientListItem> findAllPatients(Pageable pageable);

    @Query(value = "SELECT new technology.grameen.gk.health.api.responses.PatientListItem (p.id, p.pid, p.fullName, " +
            "p.gender, p.maritalStatus," +
            "p.age,p.isGB, c.name,p.guardianName,p.mobileNumber," +
            "p.createdAt, p.lastUpdatedAt) FROM Patient p LEFT JOIN p.center c" +
            " WHERE c.id=:centerId ORDER BY p.pid DESC",
            countQuery = "SELECT count(p) FROM Patient p LEFT JOIN p.center c WHERE c.id=:centerId")
    Page<PatientListItem> findByCenter(@Param("centerId") Long centerId, Pageable pageable);

    @Query(value = "SELECT new technology.grameen.gk.health.api.responses.PatientListItem (p.id, p.pid, p.fullName, " +
            "p.gender, p.maritalStatus," +
            "p.age, p.isGB,c.name,p.guardianName,p.mobileNumber," +
            "p.createdAt, p.lastUpdatedAt) FROM Patient p LEFT JOIN p.center c" +
            " WHERE c.id=:centerId AND upper(p.fullName) LIKE upper(concat('%',:fullName,'%'))",
            countQuery = "SELECT count(p) FROM Patient p LEFT JOIN p.center c WHERE c.id=:centerId AND" +
                    " upper(p.fullName) LIKE upper(concat('%',:fullName,'%'))")
    Page<PatientListItem> findByCenterAndFullName(@Param("centerId") Long centerId,
                                               @Param("fullName") String fullName,
                                               Pageable pageable);

    @Query(value = "SELECT new technology.grameen.gk.health.api.responses.PatientListItem (p.id, p.pid, p.fullName, " +
            "p.gender, p.maritalStatus," +
            "p.age,p.isGB, c.name,p.guardianName,p.mobileNumber," +
            "p.createdAt, p.lastUpdatedAt) FROM Patient p LEFT JOIN p.center c" +
            " WHERE c.id=:centerId AND p.mobileNumber LIKE %:mobileNumber%",
            countQuery = "SELECT count(p) FROM Patient p LEFT JOIN p.center c WHERE c.id=:centerId " +
                    "AND p.mobileNumber LIKE %:mobileNumber%")
    Page<PatientListItem> findByCenterAndMobileNo(@Param("centerId") Long centerId,
                                             @Param("mobileNumber") String mobileNumber,
                                             Pageable pageable);

    @Query(value = "SELECT new technology.grameen.gk.health.api.responses.PatientListItem (p.id, p.pid, p.fullName, " +
            "p.gender, p.maritalStatus," +
            "p.age,p.isGB, c.name,p.guardianName,p.mobileNumber," +
            "p.createdAt, p.lastUpdatedAt) FROM Patient p LEFT JOIN p.center c" +
            " WHERE c.id=:centerId AND p.pid LIKE %:pid%",
            countQuery = "SELECT count(p) FROM Patient p LEFT JOIN p.center c WHERE c.id=:centerId " +
                    "AND p.pid LIKE %:pid%")
    Page<PatientListItem> findByCenterAndPid(@Param("centerId") Long centerId,
                                          @Param("pid") String pid,
                                          Pageable pageable);

    @Query(value = "SELECT new technology.grameen.gk.health.api.responses.PatientListItem (p.id, p.pid, p.fullName, " +
            "p.gender, p.maritalStatus," +
            "p.age,p.isGB, c.name,p.guardianName,p.mobileNumber," +
            "p.createdAt, p.lastUpdatedAt) FROM Patient p LEFT JOIN p.center c" +
            " WHERE c.id=:centerId AND upper(p.guardianName) LIKE upper(concat('%',:name,'%'))",
            countQuery = "SELECT count(p) FROM Patient p LEFT JOIN p.center c WHERE c.id=:centerId AND" +
                    " upper(p.guardianName) LIKE upper(concat('%',:name,'%'))")
    Page<PatientListItem> findByCenterAndGuardianName(@Param("centerId") Long centerId,
                                                  @Param("name") String fullName,
                                                  Pageable pageable);

    @Query(value = "SELECT new technology.grameen.gk.health.api.responses.PatientListItem (p.id, p.pid, p.fullName, " +
            "p.gender, p.maritalStatus," +
            "p.age,p.isGB, c.name,p.guardianName,p.mobileNumber," +
            "p.createdAt, p.lastUpdatedAt) FROM Patient p LEFT JOIN p.center c" +
            " WHERE upper(p.fullName) LIKE upper(concat('%',:fullName,'%'))",
            countQuery = "SELECT count(p) FROM Patient p LEFT JOIN p.center c WHERE " +
                    " upper(p.fullName) LIKE upper(concat('%',:fullName,'%'))")
    Page<PatientListItem> findByFullName(@Param("fullName") String fullName, Pageable pageable);


    @Query(value = "SELECT new technology.grameen.gk.health.api.responses.PatientListItem (p.id, p.pid, p.fullName, " +
            "p.gender, p.maritalStatus," +
            "p.age,p.isGB, c.name,p.guardianName,p.mobileNumber," +
            "p.createdAt, p.lastUpdatedAt) FROM Patient p LEFT JOIN p.center c" +
            " WHERE p.mobileNumber LIKE %:mobileNumber%",
            countQuery = "SELECT count(p) FROM Patient p LEFT JOIN p.center c WHERE " +
                    " p.mobileNumber LIKE %:mobileNumber%")
    Page<PatientListItem> findByMobileNumber(@Param("mobileNumber") String mobileNumber, Pageable pageable);

    @Query(value = "SELECT new technology.grameen.gk.health.api.responses.PatientListItem (p.id, p.pid, p.fullName, " +
            "p.gender, p.maritalStatus," +
            "p.age,p.isGB, c.name,p.guardianName,p.mobileNumber," +
            "p.createdAt, p.lastUpdatedAt) FROM Patient p LEFT JOIN p.center c" +
            " WHERE p.pid LIKE %:pid%",
            countQuery = "SELECT count(p) FROM Patient p LEFT JOIN p.center c WHERE p.pid LIKE %:pid%")
    Page<PatientListItem> findByPid(@Param("pid") String pid,
                                             Pageable pageable);

    @Query(value = "SELECT new technology.grameen.gk.health.api.responses.PatientListItem (p.id, p.pid, p.fullName, " +
            "p.gender, p.maritalStatus," +
            "p.age,p.isGB, c.name,p.guardianName,p.mobileNumber," +
            "p.createdAt, p.lastUpdatedAt) FROM Patient p LEFT JOIN p.center c" +
            " WHERE upper(p.guardianName) LIKE upper(concat('%',:name,'%'))",
            countQuery = "SELECT count(p) FROM Patient p LEFT JOIN p.center c WHERE " +
                    " upper(p.guardianName) LIKE upper(concat('%',:name,'%'))")
    Page<PatientListItem> findByGuardianName(@Param("name") String fullName, Pageable pageable);



    @Query(value = "SELECT MAX(p.id) from Patient p")
    Integer getMaxId();

    @Query(value = "SELECT Max(r.id) FROM Patient p JOIN p.registration r")
    Integer getMaxCardRegId();

    @Query(value = "SELECT p FROM Patient p LEFT JOIN FETCH p.registration r LEFT JOIN FETCH p.patientInvoices pi WHERE p.pid = :number")
    Optional<PatientSearchResult> findByPid(@Param("number") String id);

    @Query(value = "SELECT p FROM Patient p LEFT JOIN FETCH p.registration r " +
            "LEFT JOIN FETCH p.patientInvoices pi " +
            "LEFT JOIN FETCH pi.center c WHERE p.id = :number")
    Optional<PatientSearchResult> findPatientById(@Param("number") Long id);

    List<PatientNumberAutoComplete> findByPidContainingIgnoreCase(String number);
    List<PatientNumberAutoComplete> findByPidContainingIgnoreCaseAndCenter(String number, HealthCenter center);


    @Query(value = "SELECT count(*) FROM patients WHERE " +
            "CREATED_AT BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND CENTER_ID IN :centerIds",nativeQuery = true)
    Integer getAllPatientStatsByCenters(@Param("centerIds") List<Long> centerIds, String fromDate, String toDate);

    @Query(value = "SELECT count(*) FROM patients WHERE " +
            "CREATED_AT < " +
            " TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND CENTER_ID IN :centerIds",nativeQuery = true)
    Integer getAllPatientStatsByCenters(@Param("centerIds") List<Long> centerIds,  String toDate);

    @Query(value = "SELECT count(*) FROM patients " +
            "WHERE CREATED_AT BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS')",nativeQuery = true)
    Integer getAllPatientStats(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "SELECT count(*) FROM patients " +
            "WHERE CREATED_AT < " +
            " TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS')",nativeQuery = true)
    Integer getAllPatientStats(@Param("toDate") String toDate);

    @Query(value = "SELECT COUNT(*) FROM PATIENTS p " +
            "WHERE p.ISGB = 0 " +
            "AND p.CREATED_AT BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND p.CENTER_ID IN :centerIds",nativeQuery = true)
    Integer getAllNonGbPatientStatsByCenters(@Param("centerIds") List<Long> centerIds,
                                             @Param("fromDate") String fromDate,
                                             @Param("toDate") String toDate);

    @Query(value = "SELECT COUNT(*) FROM PATIENTS p " +
            "WHERE p.ISGB = 1 " +
            "AND p.CREATED_AT BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND p.CENTER_ID IN :centerIds",nativeQuery = true)
    Integer getAllGbPatientStatsByCenters(@Param("centerIds") List<Long> centerIds,
                                          @Param("fromDate") String fromDate,
                                          @Param("toDate") String toDate);

    @Query(value = "SELECT COUNT(*) FROM PATIENTS p " +
            "WHERE p.ISGB = 1 " +
            "AND p.CREATED_AT < " +
            "TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND p.CENTER_ID IN :centerIds",nativeQuery = true)
    Integer getAllGbPatientStatsByCenters(@Param("centerIds") List<Long> centerIds,
                                          @Param("toDate") String toDate);


    @Query(value = "SELECT COUNT(*) FROM PATIENTS p " +
            "WHERE p.ISGB = 0 " +
            "AND p.CREATED_AT BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS')",nativeQuery = true)
    Integer getAllNonGbPatientStats(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "SELECT COUNT(*) FROM PATIENTS p " +
            "WHERE p.ISGB = 1 " +
            "AND p.CREATED_AT BETWEEN TO_DATE(:fromDate,'YYYY-MM-DD HH24:MI:SS') " +
            "AND TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS')",nativeQuery = true)
    Integer getAllGbPatientStats(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "SELECT COUNT(*) FROM PATIENTS p " +
            "WHERE p.ISGB = 1 " +
            "AND p.CREATED_AT < " +
            " TO_DATE(:toDate,'YYYY-MM-DD HH24:MI:SS')",nativeQuery = true)
    Integer getAllGbPatientStats(@Param("toDate") String toDate);

    List<PatientNumberAutoComplete> findByMobileNumberContainingIgnoreCase(String mobileNumber);

    List<PatientNumberAutoComplete> findByFullNameContainingIgnoreCase(String pid);

    List<PatientNumberAutoComplete> findByMobileNumberContainingIgnoreCaseAndCenter(String pid, HealthCenter center);

    List<PatientNumberAutoComplete> findByFullNameContainingIgnoreCaseAndCenter(String pid, HealthCenter center);
}
