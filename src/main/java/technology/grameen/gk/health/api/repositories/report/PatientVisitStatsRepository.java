package technology.grameen.gk.health.api.repositories.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PatientVisitStatsRepository extends ReportRepository{

    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi JOIN pi.patient p " +
            " JOIN p.registration r " +
            "WHERE p.isGB = :gb AND TO_CHAR(pi.createdAt,'yyyy-MM-dd') = :date")
    Optional<Integer> getPatientCHCount(@Param("gb") Boolean gb,
                                        @Param("date") String date);

    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi JOIN pi.patient p " +
            " JOIN p.registration r " +
            "WHERE pi.center.id IN :centers AND p.isGB = :gb AND TO_CHAR(pi.createdAt,'yyyy-MM-dd') = :date")
    Optional<Integer> getPatientCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean gb,
                                        @Param("date") String date);


    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi LEFT JOIN pi.patient p " +
            " LEFT JOIN p.registration r " +
            "WHERE r.id IS NULL AND p.isGB = :gb " +
            "AND TO_CHAR(pi.createdAt,'yyyy-MM-dd') = :date")
    Optional<Integer> getPatientNCHCount(@Param("gb") Boolean gb,
                                         @Param("date") String date);

    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi LEFT JOIN pi.patient p " +
            " LEFT JOIN p.registration r " +
            "WHERE pi.center.id IN :centers AND r.id IS NULL AND p.isGB = :gb " +
            "AND TO_CHAR(pi.createdAt,'yyyy-MM-dd') = :date")
    Optional<Integer> getPatientNCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean gb,
                                        @Param("date") String date);

    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi LEFT JOIN pi.patient p " +
            " JOIN p.registration r " +
            "WHERE p.isGB = :gb " +
            "AND TO_CHAR(pi.createdAt,'yyyy-MM') = :month")
    Optional<Integer> getMonthlyPatientCHCount(@Param("gb") Boolean gb,
                                               @Param("month") String date);

    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi LEFT JOIN pi.patient p " +
            " LEFT JOIN p.registration r " +
            "WHERE r.id IS NULL AND p.isGB = :gb " +
            "AND TO_CHAR(pi.createdAt,'yyyy-MM') = :month")
    Optional<Integer> getMonthlyPatientNCHCount(@Param("gb") Boolean gb,
                                                @Param("month") String date);

    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi LEFT JOIN pi.patient p " +
            " JOIN p.registration r " +
            "WHERE pi.center.id IN :centers AND p.isGB = :gb " +
            "AND TO_CHAR(pi.createdAt,'yyyy-MM') = :month")
    Optional<Integer> getMonthlyPatientCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean gb,
                                               @Param("month") String date);

    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi LEFT JOIN pi.patient p " +
            " LEFT JOIN p.registration r " +
            "WHERE pi.center.id IN :centers AND r.id IS NULL AND p.isGB = :gb " +
            "AND TO_CHAR(pi.createdAt,'yyyy-MM') = :month")
    Optional<Integer> getMonthlyPatientNCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean gb,
                                         @Param("month") String date);

    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi LEFT JOIN pi.patient p " +
            " JOIN p.registration r " +
            "WHERE p.isGB = :gb " +
            "AND p.createdAt BETWEEN :fromDate AND :toDate")
    Optional<Integer> getRangePatientCHCount(@Param("gb") Boolean gb,
                                             @Param("fromDate") LocalDateTime fromDate,
                                             @Param("toDate") LocalDateTime toDate);


    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi LEFT JOIN pi.patient p " +
            " LEFT JOIN p.registration r " +
            "WHERE r.id IS NULL AND p.isGB = :gb " +
            "AND p.createdAt BETWEEN :fromDate AND :toDate")
    Optional<Integer> getRangePatientNCHCount(@Param("gb") Boolean gb,
                                              @Param("fromDate") LocalDateTime fromDate,
                                              @Param("toDate") LocalDateTime toDate);

    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi LEFT JOIN pi.patient p " +
            " JOIN p.registration r " +
            "WHERE pi.center.id IN :centers AND p.isGB = :gb " +
            "AND p.createdAt BETWEEN :fromDate AND :toDate")
    Optional<Integer> getRangePatientCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean gb,
                                              @Param("fromDate") LocalDateTime fromDate,
                                              @Param("toDate") LocalDateTime toDate);


    @Query(value = "SELECT Count(pi) FROM PatientInvoice pi LEFT JOIN pi.patient p " +
            " LEFT JOIN p.registration r " +
            "WHERE pi.center.id IN :centers AND r.id IS NULL AND p.isGB = :gb " +
            "AND p.createdAt BETWEEN :fromDate AND :toDate")
    Optional<Integer> getRangePatientNCHCount(@Param("centers") List<Long> centers, @Param("gb") Boolean gb,
                                              @Param("fromDate") LocalDateTime fromDate,
                                              @Param("toDate") LocalDateTime toDate);
}
