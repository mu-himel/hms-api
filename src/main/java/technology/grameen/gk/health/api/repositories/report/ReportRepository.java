package technology.grameen.gk.health.api.repositories.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import technology.grameen.gk.health.api.entity.MonthlyStatisticalCenterWiseView;
import technology.grameen.gk.health.api.projection.MonthlyStatisticalReport;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<MonthlyStatisticalCenterWiseView,Long> {

    @Query(value = "SELECT * FROM TABLE(MSR.GET_MSR_DETAIL_REPORT(:yearMonth,:regionCode))",nativeQuery = true)
    List<MonthlyStatisticalReport> getMonthlyStatisticalReport(@Param("yearMonth") String YearMonth, @Param("regionCode") String regionCode);

    @Query(value = "SELECT * FROM TABLE(MSR.GET_MSR_DETAIL_REPORT_RANGE(:sdt,:edt,:regionCode))",nativeQuery = true)
    List<MonthlyStatisticalReport> getRangeStatisticalReport(@Param("sdt") String startDate,
                                                             @Param("edt") String endDate,
                                                             @Param("regionCode") String regionCode);




    interface ReferCenterCount{
        Long getCenterId();
        Integer getTotal();
    }
    @Query(value = "SELECT count(*) as total, hc.id as centerId FROM PRESCRIPTIONS p \n" +
            "    JOIN PATIENTS p2 ON p2.ID = p.PRESCRIPTION_PATIENT_ID \n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID  = p2.CENTER_ID \n" +
            "    WHERE NVL(hc.THIRD_LEVEL,'0') = :regionCode AND p.IS_REFER = 1 " +
            "AND TO_CHAR(p.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%' " +
            "GROUP BY hc.id",nativeQuery = true)
    List<ReferCenterCount> getReferCenterCount(@Param("regionCode") String regionCode,
                                               @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(*) as total, hc.id as centerId FROM PRESCRIPTIONS p \n" +
            "    JOIN PATIENTS p2 ON p2.ID = p.PRESCRIPTION_PATIENT_ID \n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID  = p2.CENTER_ID \n" +
            "    WHERE NVL(hc.THIRD_LEVEL,'0') = :regionCode AND p.IS_REFER = 1 " +
            "AND TO_CHAR(p.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt " +
            "GROUP BY hc.id",nativeQuery = true)
    List<ReferCenterCount> getReferCenterCount(@Param("regionCode") String regionCode,
                                               @Param("sdt") String sdt,
                                               @Param("edt") String edt);

    interface CampNo{
        Long getCenterId();
        Integer getCampNo();
        Long getEcId();
        String getEcName();
    }

    @Query(value = "SELECT hc.id as centerId, count(ec.name) AS campNo, ec.id ecId, lower(ec.NAME) ecName " +
            " FROM HEALTH_CENTERS hc " +
            " JOIN EVENTS e ON e.CENTER_ID = hc.ID\n" +
            " JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID=ec.id  AND ec.IS_SATELLITE = 0\n" +
            "WHERE e.STATUS = 'approved' AND nvl(hc.THIRD_LEVEL,'0') = :regionCode" +
            " AND TO_CHAR(e.event_date,'YYYY-MM') = :yearMonth GROUP BY hc.id,ec.id, ec.name",nativeQuery = true)
    List<CampNo> getCampNoCenterWiseEvent(@Param("yearMonth") String yearMonth,
                                          @Param("regionCode") String regionCode);


    @Query(value = "SELECT hc.id as centerId, count(ec.name) AS campNo, ec.id ecId, lower(ec.NAME) ecName " +
            " FROM HEALTH_CENTERS hc " +
            " JOIN EVENTS e ON e.CENTER_ID = hc.ID\n" +
            " JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID=ec.id  AND ec.IS_SATELLITE = 0\n" +
            "WHERE e.STATUS = 'approved' AND nvl(hc.THIRD_LEVEL,'0') = :regionCode" +
            " AND TO_CHAR(e.event_date,'YYYY-MM-DD') BETWEEN :sdt AND :edt GROUP BY hc.id,ec.id, ec.name",
            nativeQuery = true)
    List<CampNo> getCampNoCenterWiseEvent(@Param("sdt") String sdt,
                                          @Param("edt") String edt,
                                          @Param("regionCode") String regionCode);

    interface CampWiseCardMemberCount{
        Long getCenterId();
        Integer getCardMemberCount();
        Long getEcId();
        String getEcName();
    }
    @Query(value = "SELECT centerId, count(patientId) cardMemberCount, ecId,ecName from( " +
            "SELECT hc.id centerId,pr.id, p.id AS patientId, p.id AS pid,ec.id ecId, lower(ec.NAME) ecName \n" +
            "FROM HEALTH_CENTERS hc JOIN PATIENT_INVOICES pn ON pn.HEALTH_CENTER_ID = hc.id\n" +
            "JOIN patients p ON pn.PATIENT_ID = p.id\n" +
            "LEFT JOIN PATIENT_REGISTRATIONS pr ON pr.PATIENT_ID = p.id\n" +
            "JOIN EVENTS e ON pn.EVENT_ID = e.ID\n" +
            "JOIN EVENT_CATEGORIES ec ON ec.id = e.EVENT_CATEGORY_ID\n" +
            "WHERE (e.STATUS = 'approved' OR e.STATUS = 'completed') AND" +
            " TO_CHAR(e.event_date,'YYYY-MM') = :yearMonth AND nvl(hc.THIRD_LEVEL,'0')=:regionCode AND pr.id IS NOT NULL AND ec.is_satellite=0\n" +
            "GROUP BY hc.id,ec.id,ec.name, p.id,pr.id) r\n" +
            "GROUP BY r.centerId,r.ecName,r.ecId", nativeQuery = true)
    List<CampWiseCardMemberCount> getCardMemberCenterWiseEvent(@Param("yearMonth") String yearMonth,
                                                               @Param("regionCode") String regionCode);

    @Query(value = "SELECT centerId, count(patientId) cardMemberCount, ecId,ecName from( " +
            "SELECT hc.id centerId,pr.id, p.id AS patientId, p.id AS pid,ec.id ecId, lower(ec.NAME) ecName \n" +
            "FROM HEALTH_CENTERS hc JOIN PATIENT_INVOICES pn ON pn.HEALTH_CENTER_ID = hc.id\n" +
            "JOIN patients p ON pn.PATIENT_ID = p.id\n" +
            "LEFT JOIN PATIENT_REGISTRATIONS pr ON pr.PATIENT_ID = p.id\n" +
            "JOIN EVENTS e ON pn.EVENT_ID = e.ID\n" +
            "JOIN EVENT_CATEGORIES ec ON ec.id = e.EVENT_CATEGORY_ID\n" +
            "WHERE (e.STATUS = 'approved' OR e.STATUS = 'completed') AND" +
            " TO_CHAR(e.event_date,'YYYY-MM-DD') BETWEEN :sdt AND :edt AND nvl(hc.THIRD_LEVEL,'0')=:regionCode AND pr.id IS NOT NULL AND ec.is_satellite=0\n" +
            "GROUP BY hc.id,ec.id,ec.name, p.id,pr.id) r\n" +
            "GROUP BY r.centerId,r.ecName,r.ecId", nativeQuery = true)
    List<CampWiseCardMemberCount> getCardMemberCenterWiseEvent(@Param("sdt") String sdt,
                                                               @Param("edt") String edt,
                                                               @Param("regionCode") String regionCode);


    interface CampWiseNonCardMemberCount{
        Long getCenterId();
        Integer getCardMemberCount();
        Long getEcId();
        String getEcName();
    }
    @Query(value = "SELECT centerId, count(patientId) cardMemberCount, pid, ecId,ecName from( " +
            "SELECT hc.id centerId,pr.id, p.id AS patientId, p.id AS pid, ec.id ecId, lower(ec.NAME) ecName \n" +
            "FROM HEALTH_CENTERS hc JOIN PATIENT_INVOICES pn ON pn.HEALTH_CENTER_ID = hc.id\n" +
            "JOIN patients p ON pn.PATIENT_ID = p.id\n" +
            "LEFT JOIN PATIENT_REGISTRATIONS pr ON pr.PATIENT_ID = p.id\n" +
            "JOIN EVENTS e ON pn.EVENT_ID = e.ID\n" +
            "JOIN EVENT_CATEGORIES ec ON ec.id = e.EVENT_CATEGORY_ID\n" +
            "WHERE (e.STATUS = 'approved' OR e.STATUS = 'completed') AND" +
            " TO_CHAR(e.event_date,'YYYY-MM') = :yearMonth AND nvl(hc.THIRD_LEVEL,'0')=:regionCode AND pr.id IS NULL AND ec.is_satellite=0\n" +
            "GROUP BY hc.id,ec.id,ec.name, p.id,pr.id) r\n" +
            "GROUP BY r.centerId,r.ecName,r.ecId, r.pid", nativeQuery = true)
    List<CampWiseNonCardMemberCount> getNonCardMemberCenterWiseEvent(@Param("yearMonth") String yearMonth,
                                                                     @Param("regionCode") String regionCode);


    @Query(value = "SELECT centerId, count(patientId) cardMemberCount, pid, ecId,ecName from( " +
            "SELECT hc.id centerId,pr.id, p.id AS patientId, p.id AS pid, ec.id ecId, lower(ec.NAME) ecName \n" +
            "FROM HEALTH_CENTERS hc JOIN PATIENT_INVOICES pn ON pn.HEALTH_CENTER_ID = hc.id\n" +
            "JOIN patients p ON pn.PATIENT_ID = p.id\n" +
            "LEFT JOIN PATIENT_REGISTRATIONS pr ON pr.PATIENT_ID = p.id\n" +
            "JOIN EVENTS e ON pn.EVENT_ID = e.ID\n" +
            "JOIN EVENT_CATEGORIES ec ON ec.id = e.EVENT_CATEGORY_ID\n" +
            "WHERE (e.STATUS = 'approved' OR e.STATUS = 'completed') AND" +
            " TO_CHAR(e.event_date,'YYYY-MM-DD') BETWEEN :sdt AND :edt AND nvl(hc.THIRD_LEVEL,'0')=:regionCode AND pr.id IS NULL AND ec.is_satellite=0\n" +
            "GROUP BY hc.id,ec.id,ec.name, p.id,pr.id) r\n" +
            "GROUP BY r.centerId,r.ecName,r.ecId, r.pid", nativeQuery = true)
    List<CampWiseNonCardMemberCount> getNonCardMemberCenterWiseEvent(@Param("sdt") String sdt,
                                                                     @Param("edt") String edt,
                                                                     @Param("regionCode") String regionCode);


    interface SingleCampNo{
        Long getCenterId();
        Integer getCampNo();
    }
    @Query(value = "SELECT hc.id as centerId, COUNT(s.LAB_TEST_GROUP_ID) campNo FROM PATIENT_INVOICES pi2 " +
            " JOIN HEALTH_CENTERS hc ON hc.ID = pi2.HEALTH_CENTER_ID " +
            " JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id " +
            " JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID " +
            " JOIN LAB_TEST_GROUPS ltg ON ltg.ID = s.LAB_TEST_GROUP_ID " +
            " JOIN EVENTS e ON pi2.EVENT_ID = e.ID " +
            " JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID " +
            " WHERE hc.THIRD_LEVEL = :regionCode AND pi2.invoice_type='camp' " +
            " AND TO_CHAR(pi2.created_at,'YYYY-MM') LIKE :yearMonth||'%' AND lower(ec.NAME) LIKE :labTestGroup||'%' " +
            " AND lower(ltg.NAME) LIKE :labTestGroup||'%' " +
            "GROUP BY hc.id",nativeQuery = true)
    List<SingleCampNo> getCampNo(@Param("labTestGroup") String labTestGroup,
                                 @Param("regionCode") String regionCode,
                                 @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT hc.id as centerId, COUNT(s.LAB_TEST_GROUP_ID) campNo FROM PATIENT_INVOICES pi2 " +
            " JOIN HEALTH_CENTERS hc ON hc.ID = pi2.HEALTH_CENTER_ID " +
            " JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id " +
            " JOIN SERVICE s ON s.SERVICE_ID = psd.SERVICE_ID " +
            " JOIN LAB_TEST_GROUPS ltg ON ltg.ID = s.LAB_TEST_GROUP_ID " +
            " JOIN EVENTS e ON pi2.EVENT_ID = e.ID " +
            " JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID " +
            " WHERE hc.THIRD_LEVEL = :regionCode AND pi2.invoice_type='camp' " +
            " AND TO_CHAR(pi2.created_at,'YYYY-MM-DD') BETWEEN :sdt AND :edt AND lower(ec.NAME) LIKE :labTestGroup||'%' " +
            " AND lower(ltg.NAME) LIKE :labTestGroup||'%' " +
            "GROUP BY hc.id",nativeQuery = true)
    List<SingleCampNo> getCampNo(@Param("labTestGroup") String labTestGroup,
                                 @Param("regionCode") String regionCode,
                                 @Param("sdt") String sdt,
                                 @Param("edt") String edt);

    interface SingleCardMember{
        Long getCenterId();
        Integer getCardMemberNo();
    }
    @Query(value = "SELECT centerId, count(patientId) cardMemberNo from( " +
            "SELECT hc.id centerId,pr.id, p.id AS patientId, p.id AS pid " +
            "FROM HEALTH_CENTERS hc JOIN PATIENT_INVOICES pn ON pn.HEALTH_CENTER_ID = hc.id " +
            "JOIN PATIENT_INVOICES pi2 ON pi2.HEALTH_CENTER_ID = hc.ID " +
            "JOIN patients p ON pn.PATIENT_ID = p.id " +
            "LEFT JOIN PATIENT_REGISTRATIONS pr ON pr.PATIENT_ID = p.id " +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pn.id " +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID " +
            "JOIN LAB_TEST_GROUPS ltg ON ltg.ID = s.LAB_TEST_GROUP_ID " +
            "WHERE hc.THIRD_LEVEL=:regionCode AND pi2.invoice_type='camp' AND pi2.event_id is not null " +
            "AND to_char(pi2.created_at,'YYYY-MM') LIKE :yearMonth||'%' AND lower(ltg.name) LIKE :labTestGroup ||'%' AND  pr.id IS NOT NULL " +
            "GROUP BY hc.id,p.id,pr.id) r " +
            "GROUP BY r.centerId",nativeQuery = true)
    List<SingleCardMember> getCardMemberCount(@Param("labTestGroup") String ltg,
                                              @Param("regionCode") String regionCode,
                                              @Param("yearMonth") String yearMonth);


    @Query(value = "SELECT centerId, count(patientId) cardMemberNo from( " +
            "SELECT hc.id centerId,pr.id, p.id AS patientId, p.id AS pid " +
            "FROM HEALTH_CENTERS hc JOIN PATIENT_INVOICES pn ON pn.HEALTH_CENTER_ID = hc.id " +
            "JOIN PATIENT_INVOICES pi2 ON pi2.HEALTH_CENTER_ID = hc.ID " +
            "JOIN patients p ON pn.PATIENT_ID = p.id " +
            "LEFT JOIN PATIENT_REGISTRATIONS pr ON pr.PATIENT_ID = p.id " +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pn.id " +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID " +
            "JOIN LAB_TEST_GROUPS ltg ON ltg.ID = s.LAB_TEST_GROUP_ID " +
            "WHERE hc.THIRD_LEVEL=:regionCode AND pi2.invoice_type='camp' AND pi2.event_id is not null " +
            "AND to_char(pi2.created_at,'YYYY-MM-DD') BETWEEN :sdt AND :edt AND lower(ltg.name) LIKE :labTestGroup ||'%' AND  pr.id IS NOT NULL " +
            "GROUP BY hc.id,p.id,pr.id) r " +
            "GROUP BY r.centerId",nativeQuery = true)
    List<SingleCardMember> getCardMemberCount(@Param("labTestGroup") String ltg,
                                              @Param("regionCode") String regionCode,
                                              @Param("sdt") String sdt,
                                              @Param("edt") String edt);

    @Query(value = "SELECT centerId, count(patientId) cardMemberNo from( " +
            "SELECT hc.id centerId, pr.id, p.id AS patientId, p.id AS pid " +
            "FROM HEALTH_CENTERS hc JOIN PATIENT_INVOICES pn ON pn.HEALTH_CENTER_ID = hc.id " +
            "JOIN PATIENT_INVOICES pi2 ON pi2.HEALTH_CENTER_ID = hc.id " +
            "JOIN patients p ON pn.PATIENT_ID = p.id " +
            "LEFT JOIN PATIENT_REGISTRATIONS pr ON pr.PATIENT_ID = p.id " +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pn.id " +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID " +
            "JOIN LAB_TEST_GROUPS ltg ON ltg.ID = s.LAB_TEST_GROUP_ID " +
            "WHERE hc.THIRD_LEVEL=:regionCode AND to_char(pi2.created_at,'YYYY-MM') LIKE :yearMonth||'%' " +
            "AND pi2.invoice_type='camp' AND pi2.event_id is not null " +
            "AND lower(ltg.name) LIKE :labTestGroup ||'%' AND pr.id IS NULL " +
            "GROUP BY hc.id,p.id,pr.id) r " +
            "GROUP BY r.centerId",nativeQuery = true)
    List<SingleCardMember> getNonCardMemberCount(@Param("labTestGroup") String usg,
                                                 @Param("regionCode") String regionCode,
                                                @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT centerId, count(patientId) cardMemberNo from( " +
            "SELECT hc.id centerId, pr.id, p.id AS patientId, p.id AS pid " +
            "FROM HEALTH_CENTERS hc JOIN PATIENT_INVOICES pn ON pn.HEALTH_CENTER_ID = hc.id " +
            "JOIN PATIENT_INVOICES pi2 ON pi2.HEALTH_CENTER_ID = hc.id " +
            "JOIN patients p ON pn.PATIENT_ID = p.id " +
            "LEFT JOIN PATIENT_REGISTRATIONS pr ON pr.PATIENT_ID = p.id " +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pn.id " +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID " +
            "JOIN LAB_TEST_GROUPS ltg ON ltg.ID = s.LAB_TEST_GROUP_ID " +
            "WHERE hc.THIRD_LEVEL=:regionCode AND to_char(pi2.created_at,'YYYY-MM-DD') BETWEEN :sdt AND :edt " +
            "AND pi2.invoice_type='camp' AND pi2.event_id is not null " +
            "AND lower(ltg.name) LIKE :labTestGroup ||'%' AND pr.id IS NULL " +
            "GROUP BY hc.id,p.id,pr.id) r " +
            "GROUP BY r.centerId",nativeQuery = true)
    List<SingleCardMember> getNonCardMemberCount(@Param("labTestGroup") String usg,
                                                 @Param("regionCode") String regionCode,
                                                 @Param("sdt") String sdt,
                                                 @Param("edt") String edt);


    interface SchoolVisitCampStats{
        Integer getTotal();
        Long getCenterId();
    }
    @Query(value = "SELECT count(e.id) as total, CENTER_ID as centerId FROM EVENTS e \n" +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID \n" +
            "JOIN HEALTH_CENTERS hc ON e.CENTER_ID  = hc.ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')  =  :regionCode " +
            "AND lower(ec.NAME) LIKE 'school%' AND to_char(e.EVENT_DATE,'YYYY-MM') = :yearMonth\n" +
            "GROUP BY e.CENTER_ID ",nativeQuery = true)
    List<SchoolVisitCampStats> getSchoolVisitCampNo(
            @Param("regionCode") String regionCode,
            @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(e.id) as total, CENTER_ID as centerId FROM EVENTS e \n" +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID \n" +
            "JOIN HEALTH_CENTERS hc ON e.CENTER_ID  = hc.ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')  =  :regionCode " +
            "AND lower(ec.NAME) LIKE 'school%' AND to_char(e.EVENT_DATE,'YYYY-MM-DD') BETWEEN :sdt AND :edt\n" +
            "GROUP BY e.CENTER_ID ",nativeQuery = true)
    List<SchoolVisitCampStats> getSchoolVisitCampNo(
            @Param("regionCode") String regionCode,
            @Param("sdt") String sdt,
            @Param("edt") String edt);



    @Query(value = "SELECT count(pi2.PATIENT_ID) AS total, hc.ID AS centerId FROM EVENTS e \n" +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID \n" +
            "JOIN HEALTH_CENTERS hc ON e.CENTER_ID  = hc.ID \n" +
            "JOIN PATIENT_INVOICES pi2 ON e.id = pi2.EVENT_ID AND pi2.HEALTH_CENTER_ID  = hc.ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.ID \n" +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID AND s.CODE LIKE :serviceName||'%' \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')  =  :regionCode AND lower(ec.NAME) LIKE 'school%' AND to_char(pi2.CREATED_AT,'YYYY-MM') = :yearMonth\n" +
            "GROUP BY hc.ID ", nativeQuery = true)
    List<SchoolVisitCampStats> getSchoolVisitServiceCount(
            @Param("serviceName") String serviceName,
            @Param("regionCode") String regionCode,
            @Param("yearMonth") String yearMonth
            );


    @Query(value = "SELECT count(pi2.PATIENT_ID) AS total, hc.ID AS centerId FROM EVENTS e \n" +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID \n" +
            "JOIN HEALTH_CENTERS hc ON e.CENTER_ID  = hc.ID \n" +
            "JOIN PATIENT_INVOICES pi2 ON e.id = pi2.EVENT_ID AND pi2.HEALTH_CENTER_ID  = hc.ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.ID \n" +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID AND s.CODE LIKE :serviceName||'%' \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')  =  :regionCode AND lower(ec.NAME) LIKE 'school%' " +
            "AND to_char(pi2.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt\n" +
            "GROUP BY hc.ID ", nativeQuery = true)
    List<SchoolVisitCampStats> getSchoolVisitServiceCount(
            @Param("serviceName") String serviceName,
            @Param("regionCode") String regionCode,
            @Param("sdt") String sdt,
            @Param("edt") String edt
    );

    @Query(value = "SELECT count(pi2.PATIENT_ID) AS total, hc.id AS centerId FROM EVENTS e \n" +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID \n" +
            "JOIN HEALTH_CENTERS hc ON e.CENTER_ID  = hc.ID\n" +
            "JOIN PATIENT_INVOICES pi2 ON e.id = pi2.EVENT_ID AND pi2.HEALTH_CENTER_ID  = hc.ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')  = :regionCode AND lower(ec.NAME) LIKE 'school%' " +
            "AND to_char(pi2.CREATED_AT,'YYYY-MM') = :yearMonth\n" +
            "GROUP BY hc.ID",nativeQuery = true)
    List<SchoolVisitCampStats> getSchoolVisitPatientNo(
            @Param("regionCode") String regionCode,
            @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT count(pi2.PATIENT_ID) AS total, hc.id AS centerId FROM EVENTS e \n" +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID \n" +
            "JOIN HEALTH_CENTERS hc ON e.CENTER_ID  = hc.ID\n" +
            "JOIN PATIENT_INVOICES pi2 ON e.id = pi2.EVENT_ID AND pi2.HEALTH_CENTER_ID  = hc.ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')  = :regionCode AND lower(ec.NAME) LIKE 'school%' " +
            "AND to_char(pi2.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt\n" +
            "GROUP BY hc.ID",nativeQuery = true)
    List<SchoolVisitCampStats> getSchoolVisitPatientNo(
            @Param("regionCode") String regionCode,
            @Param("sdt") String sdt,
            @Param("edt") String edt);

    interface IncomeStats{
        Long getCenterId();
        String getName();
        String getThirdLevel();
        String getCenterCode();
        BigDecimal getCenterChTotal();
        BigDecimal getCenterNchTotal();
        BigDecimal getSatChTotal();
        BigDecimal getSatNchTotal();
        BigDecimal getGbCardIncome();
        BigDecimal getNgbCardIncome();
    }
    @Query(value = "SELECT id AS centerId, name, third_level AS thirdLevel, CENTER_CODE AS centerCode,\n" +
            "NVL(MSR_60.CH_BY_CENTER(id,:yearMonth),0) AS centerChTotal,\n" +
            "NVL(MSR_60.NCH_BY_CENTER(id,:yearMonth),0) AS centerNchTotal,\n" +
            "NVL(MSR_60.CH_BY_CENTER_IN_SAT(id,:yearMonth),0) AS satChTotal,\n" +
            "NVL(MSR_60.NCH_BY_CENTER_IN_SAT(id,:yearMonth),0) AS satNchTotal,\n" +
            "NVL(MSR_60.GB_CARD_INCOME(id, :yearMonth),0) AS gbCardIncome,\n"+
            "NVL(MSR_60.NGB_CARD_INCOME(id, :yearMonth),0) AS ngbCardIncome "+
            "FROM Health_centers WHERE third_level IS NOT NULL AND NVL(THIRD_LEVEL,'0') = :regionCode",
    nativeQuery = true)
    List<IncomeStats> getCenterAndSatelliteIncomes(@Param("regionCode") String regionCode,
                                                       @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT id AS centerId, name, third_level AS thirdLevel, CENTER_CODE AS centerCode,\n" +
            "NVL(MSR_60.CH_BY_CENTER_BY_RANGE(id,:sdt, :edt),0) AS centerChTotal,\n" +
            "NVL(MSR_60.NCH_BY_CENTER_BY_RANGE(id,:sdt, :edt),0) AS centerNchTotal,\n" +
            "NVL(MSR_60.CH_BY_CENTER_IN_SAT_BY_RANGE(id,:sdt, :edt),0) AS satChTotal,\n" +
            "NVL(MSR_60.NCH_BY_CENTER_IN_SAT_BY_RANGE(id,:sdt, :edt),0) AS satNchTotal,\n" +
            "NVL(MSR_60.GB_CARD_INCOME_BY_RANGE(id, :sdt, :edt),0) AS gbCardIncome,\n"+
            "NVL(MSR_60.NGB_CARD_INCOME_BY_RANGE(id, :sdt, :edt),0) AS ngbCardIncome "+
            "FROM Health_centers WHERE third_level IS NOT NULL AND NVL(THIRD_LEVEL,'0') = :regionCode",
            nativeQuery = true)
    List<IncomeStats> getCenterAndSatelliteIncomes(@Param("regionCode") String regionCode,
                                                   @Param("sdt") String sdt,
                                                   @Param("edt") String edt);

    interface EventCategoryWiseIncomeStats {
        Long getEcId();
        String getEcName();
        Integer getTotalCount();
        String getTotalAmount();
        Long getCenterId();
    }
    @Query(value = "SELECT t1.id ecId, t1.name ecName, nvl(total,0) totalCount, \n" +
            "nvl(amount,0) totalAmount,t1.center_id centerId FROM (\n" +
            "SELECT c.id,count(c.id) total, c.name,c.center_id FROM(SELECT  ec.id,ec.name,e.EVENT_DATE,e.CENTER_ID \n" +
            "    FROM EVENTS e\n" +
            "    JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "    WHERE \n" +
            "    NVL(hc.THIRD_LEVEL,'0') = :regionCode AND\n" +
            "    TO_CHAR(e.EVENT_DATE,'YYYY-MM') = :yearMonth\n" +
            "    AND e.EVENT_TYPE = 'camp' AND e.status = 'approved' GROUP BY ec.name,ec.id,e.EVENT_DATE,e.CENTER_ID) c GROUP BY c.id,c.name,c.center_id) t1\n" +
            "            LEFT JOIN (\n" +
            "SELECT sum(c.PAID_AMOUNT) amount, c.name, c.EVENT_ID, c.id id,c.event_date, c.center_id FROM (\n" +
            "SELECT SUM(psd.PAYABLE_AMOUNT) paid_amount,ec.id, ec.name,e.EVENT_DATE , pi2.PATIENT_ID,pi2.EVENT_ID ,pi2.HEALTH_CENTER_ID CENTER_ID \n" +
            "   FROM PATIENT_SERVICE_DETAILS psd\n" +
            "   JOIN  PATIENT_INVOICES pi2 ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "   JOIN HEALTH_CENTERS hc ON hc.ID = pi2.HEALTH_CENTER_ID\n" +
            "   JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "   JOIN EVENTS e ON pi2.EVENT_ID  = e.ID\n" +
            "   JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "   WHERE TO_CHAR(e.EVENT_DATE,'YYYY-MM') = :yearMonth  \n" +
            "   AND pi2.INVOICE_TYPE = 'camp' AND NVL(hc.THIRD_LEVEL,'0') = :regionCode\n" +
            "   AND psd.refunded = 0 \n" +
            "   GROUP BY pi2.PATIENT_ID,ec.id,ec.name,pi2.EVENT_ID, e.EVENT_DATE  ,pi2.HEALTH_CENTER_ID\n" +
            "   ) c GROUP BY c.name,c.id,c.EVENT_ID,c.center_id,c.event_date) t2\n" +
            "   ON t1.id = t2.id AND t1.center_id = t2.center_id",
    nativeQuery = true)
    List<EventCategoryWiseIncomeStats> getCenterCampIncomes(@Param("regionCode") String regionCode,
                                                            @Param("yearMonth") String yearMonth);


    @Query(value = "SELECT t1.id ecId, t1.name ecName, nvl(total,0) totalCount, \n" +
            "nvl(amount,0) totalAmount,t1.center_id centerId FROM (\n" +
            "SELECT c.id,count(c.id) total, c.name,c.center_id FROM(SELECT  ec.id,ec.name,e.EVENT_DATE,e.CENTER_ID \n" +
            "    FROM EVENTS e\n" +
            "    JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "    WHERE \n" +
            "    NVL(hc.THIRD_LEVEL,'0') = :regionCode AND\n" +
            "    TO_CHAR(e.EVENT_DATE,'YYYY-MM-DD') BETWEEN :sdt AND :edt\n" +
            "    AND e.EVENT_TYPE = 'camp' GROUP BY ec.name,ec.id,e.EVENT_DATE,e.CENTER_ID) c GROUP BY c.id,c.name,c.center_id) t1\n" +
            "            LEFT JOIN (\n" +
            "SELECT sum(c.PAID_AMOUNT) amount, c.name, c.EVENT_ID, c.id id,c.event_date, c.center_id FROM (\n" +
            "SELECT SUM(psd.PAYABLE_AMOUNT) paid_amount,ec.id, ec.name,e.EVENT_DATE , pi2.PATIENT_ID,pi2.EVENT_ID ,pi2.HEALTH_CENTER_ID CENTER_ID \n" +
            "   FROM PATIENT_SERVICE_DETAILS psd\n" +
            "   JOIN  PATIENT_INVOICES pi2 ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "   JOIN HEALTH_CENTERS hc ON hc.ID = pi2.HEALTH_CENTER_ID\n" +
            "   JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "   JOIN EVENTS e ON pi2.EVENT_ID  = e.ID\n" +
            "   JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "   WHERE TO_CHAR(e.EVENT_DATE,'YYYY-MM-DD') BETWEEN :sdt AND :edt  \n" +
            "   AND pi2.INVOICE_TYPE = 'camp' AND NVL(hc.THIRD_LEVEL,'0') = :regionCode\n" +
            "   AND psd.refunded = 0 \n" +
            "   GROUP BY pi2.PATIENT_ID,ec.id,ec.name,pi2.EVENT_ID, e.EVENT_DATE  ,pi2.HEALTH_CENTER_ID\n" +
            "   ) c GROUP BY c.name,c.id,c.EVENT_ID,c.center_id,c.event_date) t2\n" +
            "   ON t1.id = t2.id AND t1.center_id = t2.center_id",
            nativeQuery = true)
    List<EventCategoryWiseIncomeStats> getCenterCampIncomes(@Param("regionCode") String regionCode,
                                                            @Param("sdt") String sdt,
                                                            @Param("edt") String edt);

    @Query(value = "SELECT t1.id ecId, t1.name ecName, nvl(total,0) totalCount, \n" +
            "nvl(amount,0) totalAmount,t1.center_id centerId FROM (\n" +
            "SELECT c.id,count(c.id) total, c.name,c.center_id FROM(SELECT  ec.id,ec.name,e.EVENT_DATE,e.CENTER_ID \n" +
            "    FROM EVENTS e\n" +
            "    JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "    WHERE \n" +
            "    hc.OFFICE_TYPE_ID = 6 AND\n" +
            "    TO_CHAR(e.EVENT_DATE,'YYYY-MM') = :yearMonth \n" +
            "    AND e.EVENT_TYPE = 'camp' AND e.status = 'approved' GROUP BY ec.name,ec.id,e.EVENT_DATE,e.CENTER_ID) c GROUP BY c.id,c.name,c.center_id) t1\n" +
            "            LEFT JOIN (\n" +
            "SELECT sum(c.PAID_AMOUNT) amount, c.name, c.EVENT_ID, c.id id,c.event_date, c.center_id FROM (\n" +
            "SELECT SUM(psd.PAYABLE_AMOUNT) paid_amount,ec.id, ec.name,e.EVENT_DATE , pi2.PATIENT_ID,pi2.EVENT_ID ,pi2.HEALTH_CENTER_ID CENTER_ID \n" +
            "   FROM PATIENT_SERVICE_DETAILS psd\n" +
            "   JOIN  PATIENT_INVOICES pi2 ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "   JOIN HEALTH_CENTERS hc ON hc.ID = pi2.HEALTH_CENTER_ID\n" +
            "   JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "   JOIN EVENTS e ON pi2.EVENT_ID  = e.ID\n" +
            "   JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "   WHERE TO_CHAR(e.EVENT_DATE,'YYYY-MM') = :yearMonth  \n" +
            "   AND pi2.INVOICE_TYPE = 'camp' AND hc.OFFICE_TYPE_ID = 6 \n" +
            "   AND psd.refunded = 0 \n" +
            "   GROUP BY pi2.PATIENT_ID,ec.id,ec.name,pi2.EVENT_ID, e.EVENT_DATE  ,pi2.HEALTH_CENTER_ID\n" +
            "   ) c GROUP BY c.name,c.id,c.EVENT_ID,c.center_id,c.event_date) t2\n" +
            "   ON t1.id = t2.id AND t1.center_id = t2.center_id",
            nativeQuery = true)
    List<EventCategoryWiseIncomeStats> getCenterCampIncomesFromHO(@Param("yearMonth") String yearMonth);

    @Query(value = "SELECT t1.id ecId, t1.name ecName, nvl(total,0) totalCount, \n" +
            "nvl(amount,0) totalAmount,t1.center_id centerId FROM (\n" +
            "SELECT c.id,count(c.id) total, c.name,c.center_id FROM(SELECT  ec.id,ec.name,e.EVENT_DATE,e.CENTER_ID \n" +
            "    FROM EVENTS e\n" +
            "    JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "    WHERE \n" +
            "    NVL(hc.CENTER_CODE,'0') = :centerCode AND\n" +
            "    TO_CHAR(e.EVENT_DATE,'YYYY-MM') = :yearMonth\n" +
            "    AND e.EVENT_TYPE = 'camp' AND e.status = 'approved' GROUP BY ec.name,ec.id,e.EVENT_DATE,e.CENTER_ID) c GROUP BY c.id,c.name,c.center_id) t1\n" +
            "            LEFT JOIN (\n" +
            "SELECT sum(c.PAID_AMOUNT) amount, c.name, c.EVENT_ID, c.id id,c.event_date, c.center_id FROM (\n" +
            "SELECT SUM(psd.PAYABLE_AMOUNT) paid_amount,ec.id, ec.name,e.EVENT_DATE , pi2.PATIENT_ID,pi2.EVENT_ID ,pi2.HEALTH_CENTER_ID CENTER_ID \n" +
            "   FROM PATIENT_SERVICE_DETAILS psd\n" +
            "   JOIN  PATIENT_INVOICES pi2 ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "   JOIN HEALTH_CENTERS hc ON hc.ID = pi2.HEALTH_CENTER_ID\n" +
            "   JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "   JOIN EVENTS e ON pi2.EVENT_ID  = e.ID\n" +
            "   JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "   WHERE TO_CHAR(e.EVENT_DATE,'YYYY-MM') = :yearMonth  \n" +
            "   AND pi2.INVOICE_TYPE = 'camp' AND NVL(hc.CENTER_CODE,'0') = :centerCode\n" +
            "   AND psd.refunded = 0 \n" +
            "   GROUP BY pi2.PATIENT_ID,ec.id,ec.name,pi2.EVENT_ID, e.EVENT_DATE  ,pi2.HEALTH_CENTER_ID\n" +
            "   ) c GROUP BY c.name,c.id,c.EVENT_ID,c.center_id,c.event_date) t2\n" +
            "   ON t1.id = t2.id AND t1.center_id = t2.center_id",
            nativeQuery = true)
    List<EventCategoryWiseIncomeStats> getCenterCampIncomesByCenter(@Param("centerCode") String centerCode,
                                                            @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT t1.id ecId, t1.name ecName, nvl(total,0) totalCount, \n" +
            "nvl(amount,0) totalAmount,t1.center_id centerId FROM (\n" +
            "SELECT c.id,count(c.id) total, c.name,c.center_id FROM(SELECT  ec.id,ec.name,e.EVENT_DATE,e.CENTER_ID \n" +
            "    FROM EVENTS e\n" +
            "    JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "    WHERE \n" +
            "    NVL(hc.THIRD_LEVEL,'0') = :regionCode AND\n" +
            "    e.EVENT_DATE BETWEEN :startDate AND :endDate\n" +
            "    AND e.EVENT_TYPE = 'camp' AND e.status = 'approved' GROUP BY ec.name,ec.id,e.EVENT_DATE,e.CENTER_ID) c GROUP BY c.id,c.name,c.center_id) t1\n" +
            "            LEFT JOIN (\n" +
            "SELECT sum(c.PAID_AMOUNT) amount, c.name, c.EVENT_ID, c.id id,c.event_date, c.center_id FROM (\n" +
            "SELECT SUM(psd.PAYABLE_AMOUNT) paid_amount,ec.id, ec.name,e.EVENT_DATE , pi2.PATIENT_ID,pi2.EVENT_ID ,pi2.HEALTH_CENTER_ID CENTER_ID \n" +
            "   FROM PATIENT_SERVICE_DETAILS psd\n" +
            "   JOIN  PATIENT_INVOICES pi2 ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "   JOIN HEALTH_CENTERS hc ON hc.ID = pi2.HEALTH_CENTER_ID\n" +
            "   JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "   JOIN EVENTS e ON pi2.EVENT_ID  = e.ID\n" +
            "   JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "   WHERE e.EVENT_DATE BETWEEN :startDate AND :endDate  \n" +
            "   AND pi2.INVOICE_TYPE = 'camp' AND NVL(hc.THIRD_LEVEL,'0') = :regionCode\n" +
            "   AND psd.refunded = 0 \n" +
            "   GROUP BY pi2.PATIENT_ID,ec.id,ec.name,pi2.EVENT_ID, e.EVENT_DATE  ,pi2.HEALTH_CENTER_ID\n" +
            "   ) c GROUP BY c.name,c.id,c.EVENT_ID,c.center_id,c.event_date) t2\n" +
            "   ON t1.id = t2.id AND t1.center_id = t2.center_id",
            nativeQuery = true)
    List<EventCategoryWiseIncomeStats> getCenterCampIncomesByRange(@Param("regionCode") String regionCode,
                                                            @Param("startDate") LocalDateTime startDate,
                                                                   @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT t1.id ecId, t1.name ecName, nvl(total,0) totalCount, \n" +
            "nvl(amount,0) totalAmount,t1.center_id centerId FROM (\n" +
            "SELECT c.id,count(c.id) total, c.name,c.center_id FROM(SELECT  ec.id,ec.name,e.EVENT_DATE,e.CENTER_ID \n" +
            "    FROM EVENTS e\n" +
            "    JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "    WHERE \n" +
            "    hc.OFFICE_TYPE_ID = 6 AND\n" +
            "    e.EVENT_DATE BETWEEN :startDate AND :endDate\n" +
            "    AND e.EVENT_TYPE = 'camp' AND e.status = 'approved' GROUP BY ec.name,ec.id,e.EVENT_DATE,e.CENTER_ID) c GROUP BY c.id,c.name,c.center_id) t1\n" +
            "            LEFT JOIN (\n" +
            "SELECT sum(c.PAID_AMOUNT) amount, c.name, c.EVENT_ID, c.id id,c.event_date, c.center_id FROM (\n" +
            "SELECT SUM(psd.PAYABLE_AMOUNT) paid_amount,ec.id, ec.name,e.EVENT_DATE , pi2.PATIENT_ID,pi2.EVENT_ID ,pi2.HEALTH_CENTER_ID CENTER_ID \n" +
            "   FROM PATIENT_SERVICE_DETAILS psd\n" +
            "   JOIN  PATIENT_INVOICES pi2 ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "   JOIN HEALTH_CENTERS hc ON hc.ID = pi2.HEALTH_CENTER_ID\n" +
            "   JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "   JOIN EVENTS e ON pi2.EVENT_ID  = e.ID\n" +
            "   JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "   WHERE e.EVENT_DATE BETWEEN :startDate AND :endDate  \n" +
            "   AND pi2.INVOICE_TYPE = 'camp' AND hc.OFFICE_TYPE_ID=6\n" +
            "   AND psd.refunded = 0 \n" +
            "   GROUP BY pi2.PATIENT_ID,ec.id,ec.name,pi2.EVENT_ID, e.EVENT_DATE  ,pi2.HEALTH_CENTER_ID\n" +
            "   ) c GROUP BY c.name,c.id,c.EVENT_ID,c.center_id,c.event_date) t2\n" +
            "   ON t1.id = t2.id AND t1.center_id = t2.center_id",
            nativeQuery = true)
    List<EventCategoryWiseIncomeStats> getCenterCampIncomesByRangeFromHo(@Param("startDate") LocalDateTime startDate,
                                                                   @Param("endDate") LocalDateTime endDate);


    @Query(value = "SELECT t1.id ecId, t1.name ecName, nvl(total,0) totalCount, \n" +
            "nvl(amount,0) totalAmount,t1.center_id centerId FROM (\n" +
            "SELECT c.id,count(c.id) total, c.name,c.center_id FROM(SELECT  ec.id,ec.name,e.EVENT_DATE,e.CENTER_ID \n" +
            "    FROM EVENTS e\n" +
            "    JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "    WHERE \n" +
            "    NVL(hc.CENTER_CODE,'0') = :centerCode AND\n" +
            "    e.EVENT_DATE BETWEEN :startDate AND :endDate\n" +
            "    AND e.EVENT_TYPE = 'camp' AND e.status = 'approved' GROUP BY ec.name,ec.id,e.EVENT_DATE,e.CENTER_ID) c GROUP BY c.id,c.name,c.center_id) t1\n" +
            "            LEFT JOIN (\n" +
            "SELECT sum(c.PAID_AMOUNT) amount, c.name, c.EVENT_ID, c.id id,c.event_date, c.center_id FROM (\n" +
            "SELECT SUM(psd.PAYABLE_AMOUNT) paid_amount,ec.id, ec.name,e.EVENT_DATE , pi2.PATIENT_ID,pi2.EVENT_ID ,pi2.HEALTH_CENTER_ID CENTER_ID \n" +
            "   FROM PATIENT_SERVICE_DETAILS psd\n" +
            "   JOIN  PATIENT_INVOICES pi2 ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "   JOIN HEALTH_CENTERS hc ON hc.ID = pi2.HEALTH_CENTER_ID\n" +
            "   JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID\n" +
            "   JOIN EVENTS e ON pi2.EVENT_ID  = e.ID\n" +
            "   JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "   WHERE e.EVENT_DATE BETWEEN :startDate AND :endDate  \n" +
            "   AND pi2.INVOICE_TYPE = 'camp' AND NVL(hc.CENTER_CODE,'0') = :centerCode\n" +
            "   AND psd.refunded = 0 \n" +
            "   GROUP BY pi2.PATIENT_ID,ec.id,ec.name,pi2.EVENT_ID, e.EVENT_DATE  ,pi2.HEALTH_CENTER_ID\n" +
            "   ) c GROUP BY c.name,c.id,c.EVENT_ID,c.center_id,c.event_date) t2\n" +
            "   ON t1.id = t2.id AND t1.center_id = t2.center_id",
            nativeQuery = true)
    List<EventCategoryWiseIncomeStats> getCenterCampIncomesByRangeByCenter(@Param("centerCode") String centerCode,
                                                                   @Param("startDate") LocalDateTime startDate,
                                                                   @Param("endDate") LocalDateTime endDate);

    interface ServiceCategoryIncome{
        BigDecimal getTotalAmount();
        Long getCenterId();
        Long getServiceId();
        String getServiceName();
    }

    @Query(value = "SELECT NVL(SUM(pi2.PAID_AMOUNT),0) as totalAmount," +
            "pi2.HEALTH_CENTER_ID as centerId, s.SERVICE_ID as serviceId,s.NAME as serviceName FROM patient_invoices pi2\n" +
            "JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND lower(sc.NAME) LIKE '%'||:serviceCategory||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%' " +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,s.NAME",nativeQuery = true)
    List<ServiceCategoryIncome> getIncomeByServiceCategory(@Param("regionCode") String regionCode,
                                         @Param("yearMonth") String yearMonth,
                                                   @Param("serviceCategory") String serviceCategory);


    @Query(value = "SELECT NVL(SUM(pi2.PAID_AMOUNT),0) as totalAmount," +
            "pi2.HEALTH_CENTER_ID as centerId, s.SERVICE_ID as serviceId,s.NAME as serviceName FROM patient_invoices pi2\n" +
            "JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND lower(sc.NAME) LIKE '%'||:serviceCategory||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt " +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,s.NAME",nativeQuery = true)
    List<ServiceCategoryIncome> getIncomeByServiceCategory(@Param("regionCode") String regionCode,
                                                           @Param("sdt") String sdt,
                                                           @Param("edt") String edt,
                                                           @Param("serviceCategory") String serviceCategory);


    interface SafetyNetCount{
        Integer getTotal();
        Long getCenterId();
        Long getServiceId();
    }

    @Query(value = "SELECT COUNT(pi2.PAID_AMOUNT) as total,pi2.HEALTH_CENTER_ID as centerId,s.SERVICE_ID as serviceId\n" +
            "FROM patient_invoices pi2\n" +
            "JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND lower(s.NAME) LIKE 'free prescription'||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%' AND GET_CURRENT_YEAR(p.CREATED_AT,p.age)>=60\n" +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID",nativeQuery = true)
    List<SafetyNetCount> getSafetyNetCount(@Param("regionCode") String regionCode,
                                           @Param("yearMonth") String month);

    @Query(value = "SELECT COUNT(pi2.PAID_AMOUNT) as total,pi2.HEALTH_CENTER_ID as centerId,s.SERVICE_ID as serviceId\n" +
            "FROM patient_invoices pi2\n" +
            "JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND lower(s.NAME) LIKE 'free prescription'||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt AND GET_CURRENT_YEAR(p.CREATED_AT,p.age)>=60\n" +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID",nativeQuery = true)
    List<SafetyNetCount> getSafetyNetCount(@Param("regionCode") String regionCode,
                                           @Param("sdt") String sdt,
                                           @Param("edt") String edt);

    interface DeliveryCount{
        Integer getTotal();
        Long getCenterId();
        Long getRegionId();
        Long getServiceId();
    }
    @Query(value = "SELECT COUNT(pi2.PAID_AMOUNT) as total,pi2.HEALTH_CENTER_ID as centerId,s.SERVICE_ID as serviceId\n" +
            "FROM patient_invoices pi2\n" +
            "JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND lower(s.NAME) LIKE 'delivery'||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%' \n" +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID",nativeQuery = true)
    List<DeliveryCount> getCenterWiseDeliveryCount(@Param("regionCode") String regionCode,
                                                   @Param("yearMonth") String yearMonth);

    @Query(value = "SELECT COUNT(pi2.PAID_AMOUNT) as total,pi2.HEALTH_CENTER_ID as centerId,s.SERVICE_ID as serviceId\n" +
            "FROM patient_invoices pi2\n" +
            "JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND lower(s.NAME) LIKE 'delivery'||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt \n" +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID",nativeQuery = true)
    List<DeliveryCount> getCenterWiseDeliveryCount(@Param("regionCode") String regionCode,
                                                   @Param("sdt") String sdt,
                                                   @Param("edt") String edt);

    @Query(value = "SELECT COUNT(pi2.PAID_AMOUNT) as total,pi2.HEALTH_CENTER_ID as centerId," +
            "s.SERVICE_ID as serviceId, region.region_id regionId\n" +
            "FROM patient_invoices pi2\n" +
            "JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN (SELECT hc1.id region_Id,p.id center_Id FROM HEALTH_CENTERS hc1 \n" +
            "           JOIN (SELECT id,THIRD_LEVEL, FOURTH_LEVEL FROM HEALTH_CENTERS hc) p\n" +
            "           ON hc1.CENTER_CODE = p.THIRD_LEVEL) region " +
            " ON region.center_id = pi2.health_center_id " +
            "WHERE lower(s.NAME) LIKE 'delivery'||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') < :edt \n" +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,region.region_id",nativeQuery = true)
    List<DeliveryCount> getDeliveryCountTillLastMonth(@Param("edt") String edt);

    @Query(value = "SELECT COUNT(pi2.PAID_AMOUNT) as total,pi2.HEALTH_CENTER_ID as centerId," +
            "region.region_id regionId,s.SERVICE_ID as serviceId\n" +
            "FROM patient_invoices pi2\n" +
            "JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN (SELECT hc1.id region_Id,p.id center_Id FROM HEALTH_CENTERS hc1 \n" +
            "           JOIN (SELECT id,THIRD_LEVEL, FOURTH_LEVEL FROM HEALTH_CENTERS hc) p\n" +
            "           ON hc1.CENTER_CODE = p.THIRD_LEVEL ) region\n" +
            "ON region.center_id = pi2.health_center_id\n" +
            "WHERE pi2.HEALTH_CENTER_ID IN :centers AND lower(s.NAME) LIKE 'delivery'||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') < :edt \n" +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID, region.region_id",nativeQuery = true)
    List<DeliveryCount> getDeliveryCountTillLastMonth(@Param("edt") String edt,
                                                      @Param("centers") List<Long> centers);

    @Query(value = "SELECT COUNT(pi2.PAID_AMOUNT) as total,pi2.HEALTH_CENTER_ID as centerId," +
            "region.region_id regionId, s.SERVICE_ID as serviceId\n" +
            "FROM patient_invoices pi2\n" +
            "JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN (SELECT hc1.id region_Id,p.id center_Id FROM HEALTH_CENTERS hc1 \n" +
            "           JOIN (SELECT id,THIRD_LEVEL, FOURTH_LEVEL FROM HEALTH_CENTERS hc) p\n" +
            "           ON hc1.CENTER_CODE = p.THIRD_LEVEL) region " +
            "ON region.center_id = pi2.health_center_id\n" +
            "WHERE lower(s.NAME) LIKE 'delivery'||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') = :month \n" +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID, region.region_id",nativeQuery = true)
    List<DeliveryCount> getDeliveryCountByMonth(@Param("month") String month);

    @Query(value = "SELECT COUNT(pi2.PAID_AMOUNT) as total,pi2.HEALTH_CENTER_ID as centerId," +
            "region.region_id regionId,s.SERVICE_ID as serviceId\n" +
            "FROM patient_invoices pi2\n" +
            "JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN (SELECT hc1.id region_Id,p.id center_Id FROM HEALTH_CENTERS hc1 \n" +
            "           JOIN (SELECT id,THIRD_LEVEL, FOURTH_LEVEL FROM HEALTH_CENTERS hc) p\n" +
            "           ON hc1.CENTER_CODE = p.THIRD_LEVEL) region\n" +
            "ON region.center_id = pi2.health_center_id\n" +
            "WHERE pi2.HEALTH_CENTER_ID IN :centers AND lower(s.NAME) LIKE 'delivery'||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') = :month \n" +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID, region.region_id",nativeQuery = true)
    List<DeliveryCount> getDeliveryCountByMonth(@Param("month") String month,
                                                @Param("centers") List<Long> centers);

    interface PregnantRegistered {
        Long getTotal();
        Long getCenterId();
        Long getRegionId();
    }
    @Query(value = "SELECT count(*) total, pdp.CENTER_ID centerId FROM PATIENT_DISEASE_PROFILES pdp \n" +
            "JOIN DISEASE_PROFILES dp ON dp.ID = pdp.DISEASE_PROFILE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID = pdp.CENTER_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND dp.ALIAS = 'pregnant' " +
            "AND TO_CHAR(pdp.CREATED_AT,'YYYY-MM') = :yearMonth \n" +
            "GROUP BY pdp.CENTER_ID ",nativeQuery = true)
    List<PregnantRegistered> getMonthWisePregnantRegistered(@Param("yearMonth") String yearMonth,
                                                            @Param("regionCode") String regionCode);

    @Query(value = "SELECT count(*) total, pdp.CENTER_ID centerId, region.region_id regionId" +
            " FROM PATIENT_DISEASE_PROFILES pdp \n" +
            "JOIN DISEASE_PROFILES dp ON dp.ID = pdp.DISEASE_PROFILE_ID \n" +
            "JOIN (SELECT hc1.id region_Id,p.id center_Id FROM HEALTH_CENTERS hc1 \n" +
            "           JOIN (SELECT id,THIRD_LEVEL, FOURTH_LEVEL FROM HEALTH_CENTERS hc) p\n" +
            "           ON hc1.CENTER_CODE = p.THIRD_LEVEL) region ON region.center_Id = pdp.center_id\n" +
            "WHERE dp.ALIAS = 'pregnant' " +
            "AND TO_CHAR(pdp.CREATED_AT,'YYYY-MM-DD') < :endMonth \n" +
            "GROUP BY pdp.CENTER_ID,region.region_id ",nativeQuery = true)
    List<PregnantRegistered> getPregnantRegisteredTillLastMonth(@Param("endMonth") String endMonth);

    @Query(value = "SELECT count(*) total, pdp.CENTER_ID centerId,region.region_id regionId FROM PATIENT_DISEASE_PROFILES pdp \n" +
            "JOIN DISEASE_PROFILES dp ON dp.ID = pdp.DISEASE_PROFILE_ID \n" +
            "JOIN (SELECT hc1.id region_Id,p.id center_Id FROM HEALTH_CENTERS hc1 \n" +
            "           JOIN (SELECT id,THIRD_LEVEL, FOURTH_LEVEL FROM HEALTH_CENTERS hc) p\n" +
            "           ON hc1.CENTER_CODE = p.THIRD_LEVEL ) region\n" +
            "ON region.center_id = pdp.center_id \n" +
            "WHERE pdp.CENTER_ID IN :centers AND dp.ALIAS = 'pregnant' " +
            "AND TO_CHAR(pdp.CREATED_AT,'YYYY-MM-DD') < :endMonth \n" +
            "GROUP BY pdp.CENTER_ID,region.region_id ",nativeQuery = true)
    List<PregnantRegistered> getPregnantRegisteredTillLastMonth(@Param("endMonth") String endMonth,
                                                                @Param("centers") List<Long> centers);

    @Query(value = "SELECT count(*) total, pdp.CENTER_ID centerId,region.region_id regionId" +
            " FROM PATIENT_DISEASE_PROFILES pdp \n" +
            "JOIN DISEASE_PROFILES dp ON dp.ID = pdp.DISEASE_PROFILE_ID \n" +
            "JOIN (SELECT hc1.id region_Id,p.id center_Id FROM HEALTH_CENTERS hc1 \n" +
            "           JOIN (SELECT id,THIRD_LEVEL, FOURTH_LEVEL FROM HEALTH_CENTERS hc) p\n" +
            "           ON hc1.CENTER_CODE = p.THIRD_LEVEL ) region\n" +
            "ON region.center_id = pdp.center_id\n" +
            "WHERE dp.ALIAS = 'pregnant' " +
            "AND TO_CHAR(pdp.END_DATE,'YYYY-MM') = :month \n" +
            "GROUP BY pdp.CENTER_ID,region.region_id ",nativeQuery = true)
    List<PregnantRegistered> getDeliveryProbabilityCount(@Param("month") String month);

    @Query(value = "SELECT count(*) total, pdp.CENTER_ID centerId, region.region_id regionId\n" +
            "FROM PATIENT_DISEASE_PROFILES pdp \n" +
            "JOIN DISEASE_PROFILES dp ON dp.ID = pdp.DISEASE_PROFILE_ID \n" +
            "JOIN (SELECT hc1.id region_Id,p.id center_Id FROM HEALTH_CENTERS hc1 \n" +
            "           JOIN (SELECT id,THIRD_LEVEL, FOURTH_LEVEL FROM HEALTH_CENTERS hc) p\n" +
            "           ON hc1.CENTER_CODE = p.THIRD_LEVEL) region\n" +
            "ON region.center_id = pdp.center_id \n" +
            "WHERE pdp.CENTER_ID IN :centers AND dp.ALIAS = 'pregnant' " +
            "AND TO_CHAR(pdp.END_DATE,'YYYY-MM') = :month \n" +
            "GROUP BY pdp.CENTER_ID,region.region_id ",nativeQuery = true)
    List<PregnantRegistered> getDeliveryProbabilityCount(@Param("month") String month,
                                                         @Param("centers") List<Long> centers);

    @Query(value = "SELECT count(*) total, pdp.CENTER_ID centerId, region.region_id regionId" +
            " FROM PATIENT_DISEASE_PROFILES pdp \n" +
            "JOIN DISEASE_PROFILES dp ON dp.ID = pdp.DISEASE_PROFILE_ID \n" +
            "JOIN (SELECT hc1.id region_id,p.id center_id FROM HEALTH_CENTERS hc1 \n" +
            "           JOIN (SELECT id,THIRD_LEVEL, FOURTH_LEVEL FROM HEALTH_CENTERS hc) p\n" +
            "           ON hc1.CENTER_CODE = p.THIRD_LEVEL ) region " +
            "ON region.center_id = pdp.center_id\n" +
            "WHERE dp.ALIAS = 'pregnant' " +
            "AND TO_CHAR(pdp.CREATED_AT,'YYYY-MM') = :endMonth \n" +
            "GROUP BY pdp.CENTER_ID, region.region_id ",nativeQuery = true)
    List<PregnantRegistered> getPregnantRegisteredThisMonth(@Param("endMonth") String endMonth);

    @Query(value = "SELECT count(*) total, pdp.CENTER_ID centerId,region.region_id regionId\n" +
            " FROM PATIENT_DISEASE_PROFILES pdp \n" +
            "JOIN DISEASE_PROFILES dp ON dp.ID = pdp.DISEASE_PROFILE_ID \n" +
            "JOIN (SELECT hc1.id region_Id,p.id center_Id FROM HEALTH_CENTERS hc1 \n" +
            "           JOIN (SELECT id,THIRD_LEVEL, FOURTH_LEVEL FROM HEALTH_CENTERS hc) p\n" +
            "           ON hc1.CENTER_CODE = p.THIRD_LEVEL ) region\n" +
            "ON region.center_id = pdp.center_id \n" +
            "WHERE pdp.CENTER_ID IN :centers AND dp.ALIAS = 'pregnant' " +
            "AND TO_CHAR(pdp.CREATED_AT,'YYYY-MM') = :endMonth \n" +
            "GROUP BY pdp.CENTER_ID,region.region_id ",nativeQuery = true)
    List<PregnantRegistered> getPregnantRegisteredThisMonth(@Param("endMonth") String endMonth,
                                                            @Param("centers") List<Long> centers);

    @Query(value = "SELECT count(*) total, pdp.CENTER_ID centerId FROM PATIENT_DISEASE_PROFILES pdp \n" +
            "JOIN DISEASE_PROFILES dp ON dp.ID = pdp.DISEASE_PROFILE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID = pdp.CENTER_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND dp.ALIAS = 'pregnant' " +
            "AND TO_CHAR(pdp.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt \n" +
            "GROUP BY pdp.CENTER_ID ",nativeQuery = true)
    List<PregnantRegistered> getDateRangeWisePregnantRegistered(@Param("sdt") String sdt,
                                                            @Param("edt") String edt,
                                                            @Param("regionCode") String regionCode);


    interface VaccineCount{
        Integer getTotal();
        Long getCenterId();
        Long getServiceId();
        Integer getAge();
        String getServiceName();
    }
    @Query(value = "SELECT NVL(COUNT(pi2.PAID_AMOUNT),0) as total, p.age,\n" +
            "    pi2.HEALTH_CENTER_ID as centerId, s.SERVICE_ID as serviceId,s.NAME as serviceName FROM patient_invoices pi2\n" +
            "    JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "    JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "    JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "    JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "    JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID \n" +
            "    WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND lower(sc.NAME) LIKE '%'||'vaccine'||'%'\n" +
            "    AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%'\n" +
            "    AND GET_CURRENT_YEAR(p.CREATED_AT,p.age)<18\n" +
            "    GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,s.NAME,p.age",nativeQuery = true)
    List<VaccineCount> getCenterWiseChildVaccinationCount(@Param("regionCode") String regionCode,
                                                     @Param("yearMonth") String yearMonth
                                                     );

    @Query(value = "SELECT NVL(COUNT(pi2.PAID_AMOUNT),0) as total, p.age,\n" +
            "    pi2.HEALTH_CENTER_ID as centerId, s.SERVICE_ID as serviceId,s.NAME as serviceName FROM patient_invoices pi2\n" +
            "    JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "    JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "    JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "    JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "    JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID \n" +
            "    WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND lower(sc.NAME) LIKE '%'||'vaccine'||'%'\n" +
            "    AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt\n" +
            "    AND GET_CURRENT_YEAR(p.CREATED_AT,p.age)<18\n" +
            "    GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,s.NAME,p.age",nativeQuery = true)
    List<VaccineCount> getCenterWiseChildVaccinationCount(@Param("regionCode") String regionCode,
                                                          @Param("sdt") String sdt,
                                                          @Param("edt") String edt
    );

    @Query(value = "SELECT NVL(COUNT(pi2.PAID_AMOUNT),0) as total, p.age,\n" +
            "    pi2.HEALTH_CENTER_ID as centerId, s.SERVICE_ID as serviceId,s.NAME as serviceName FROM patient_invoices pi2\n" +
            "    JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "    JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "    JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "    JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "    JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID \n" +
            "    WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND lower(sc.NAME) LIKE '%'||'vaccine'||'%'\n" +
            "    AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%'\n" +
            "    AND GET_CURRENT_YEAR(p.CREATED_AT,p.age)>=18\n" +
            "    GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,s.NAME,p.age",nativeQuery = true)
    List<VaccineCount> getCenterWiseAdultVaccinationCount(@Param("regionCode") String regionCode,
                                                          @Param("yearMonth") String yearMonth
    );


    @Query(value = "SELECT NVL(COUNT(pi2.PAID_AMOUNT),0) as total, p.age,\n" +
            "    pi2.HEALTH_CENTER_ID as centerId, s.SERVICE_ID as serviceId,s.NAME as serviceName FROM patient_invoices pi2\n" +
            "    JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "    JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "    JOIN PATIENTS p ON p.id = pi2.PATIENT_ID \n" +
            "    JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "    JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID \n" +
            "    WHERE NVL(hc.THIRD_LEVEL,'0')=:regionCode AND lower(sc.NAME) LIKE '%'||'vaccine'||'%'\n" +
            "    AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') BETWEEN :sdt AND :edt\n" +
            "    AND GET_CURRENT_YEAR(p.CREATED_AT,p.age)>=18\n" +
            "    GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,s.NAME,p.age",nativeQuery = true)
    List<VaccineCount> getCenterWiseAdultVaccinationCount(@Param("regionCode") String regionCode,
                                                          @Param("sdt") String sdt,
                                                          @Param("edt") String edt
    );


}
