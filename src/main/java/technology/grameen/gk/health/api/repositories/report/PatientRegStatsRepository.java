package technology.grameen.gk.health.api.repositories.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PatientRegStatsRepository extends ReportRepository{

    @Query(value = "SELECT Count(p) FROM Patient p JOIN p.registration r " +
            "WHERE p.center.id IN :centers AND p.isGB = :gb AND TO_CHAR(p.createdAt,'yyyy-MM-dd') = :date")
    Optional<Integer> getPatientCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean gb,
                                        @Param("date") String date);

    @Query(value = "SELECT Count(p) FROM Patient p JOIN p.registration r " +
            "WHERE p.center.id IN :centers AND p.isGB = :gb AND TO_CHAR(p.createdAt,'yyyy-MM') = :month")
    Optional<Integer> getMonthlyPatientCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean b,
                                               @Param("month") String month);

    @Query(value = "SELECT Count(p) FROM Patient p JOIN p.registration r " +
            "WHERE p.center.id IN :centers AND p.isGB = :gb AND p.createdAt BETWEEN :fromDate AND :toDate")
    Optional<Integer> getRangePatientCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean b,
                                             @Param("fromDate") LocalDateTime fromDate,
                                             @Param("toDate") LocalDateTime toDate);

    @Query(value = "SELECT Count(p) FROM Patient p LEFT JOIN p.registration r " +
            "WHERE p.center.id IN :centers AND r.id IS NULL AND p.isGB = :gb AND TO_CHAR(p.createdAt,'yyyy-MM-dd') = :date")
    Optional<Integer> getPatientNCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean gb,
                                         @Param("date") String date);

    @Query(value = "SELECT Count(p) FROM Patient p LEFT JOIN p.registration r " +
            "WHERE p.center.id IN :centers AND r.id IS NULL AND p.isGB = :gb AND TO_CHAR(p.createdAt,'yyyy-MM') = :month")
    Optional<Integer> getMonthlyPatientNCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean b,
                                                @Param("month") String month);

    @Query(value = "SELECT Count(p) FROM Patient p LEFT JOIN p.registration r " +
            "WHERE p.center.id IN :centers AND r.id IS NULL AND p.isGB = :gb AND p.createdAt BETWEEN :fromDate AND :toDate")
    Optional<Integer> getRangePatientNCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean b,
                                              @Param("fromDate") LocalDateTime fromDate,
                                              @Param("toDate") LocalDateTime toDate);


    @Query(value = "SELECT Count(p) FROM Patient p JOIN p.registration r " +
            "WHERE p.isGB = :gb AND TO_CHAR(p.createdAt,'yyyy-MM-dd') = :date")
    Optional<Integer> getPatientCHCount(@Param("gb") Boolean gb,
                                        @Param("date") String date);

    @Query(value = "SELECT Count(p) FROM Patient p JOIN p.registration r " +
            "WHERE p.isGB = :gb AND TO_CHAR(p.createdAt,'yyyy-MM') = :month")
    Optional<Integer> getMonthlyPatientCHCount(@Param("gb") Boolean b,
                                               @Param("month") String month);

    @Query(value = "SELECT Count(p) FROM Patient p JOIN p.registration r " +
            "WHERE p.isGB = :gb AND p.createdAt BETWEEN :fromDate AND :toDate")
    Optional<Integer> getRangePatientCHCount(@Param("gb") Boolean b,
                                             @Param("fromDate") LocalDateTime fromDate,
                                             @Param("toDate") LocalDateTime toDate);

    @Query(value = "SELECT Count(p) FROM Patient p LEFT JOIN p.registration r " +
            "WHERE r.id IS NULL AND p.isGB = :gb AND TO_CHAR(p.createdAt,'yyyy-MM-dd') = :date")
    Optional<Integer> getPatientNCHCount(@Param("gb") Boolean gb,
                                         @Param("date") String date);

    @Query(value = "SELECT Count(p) FROM Patient p LEFT JOIN p.registration r " +
            "WHERE r.id IS NULL AND p.isGB = :gb AND TO_CHAR(p.createdAt,'yyyy-MM') = :month")
    Optional<Integer> getMonthlyPatientNCHCount(@Param("gb") Boolean b,
                                                @Param("month") String month);

    @Query(value = "SELECT Count(p) FROM Patient p LEFT JOIN p.registration r " +
            "WHERE r.id IS NULL AND p.isGB = :gb AND p.createdAt BETWEEN :fromDate AND :toDate")
    Optional<Integer> getRangePatientNCHCount(@Param("gb") Boolean b,
                                              @Param("fromDate") LocalDateTime fromDate,
                                              @Param("toDate") LocalDateTime toDate);

    @Query(value = "SELECT count(p.id) total\n" +
            "FROM PATIENTS p",nativeQuery = true)
    Optional<Integer> getTotalPatientRegistrationCount();

    @Query(value = "SELECT count(p.id) total\n" +
            "FROM PATIENTS p WHERE p.CENTER_ID IN :centers",nativeQuery = true)
    Optional<Integer> getTotalPatientRegistrationCount(@Param("centers") List<Long> centers);
}
