package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import technology.grameen.gk.health.api.entity.MonthlyStatisticalCenterWiseView;
import technology.grameen.gk.health.api.projection.MonthlyStatisticalReport;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<MonthlyStatisticalCenterWiseView,Long> {

    @Query(value = "SELECT * FROM TABLE(MSR.GET_MSR_DETAIL_REPORT(:yearMonth,:regionCode))",nativeQuery = true)
    List<MonthlyStatisticalReport> getMonthlyStatisticalReport(@Param("yearMonth") String YearMonth, @Param("regionCode") String regionCode);

    interface ReferCenterCount{
        Long getCenterId();
        Integer getTotal();
    }
    @Query(value = "SELECT count(*) as total, hc.id as centerId FROM PRESCRIPTIONS p \n" +
            "    JOIN PATIENTS p2 ON p2.ID = p.PRESCRIPTION_PATIENT_ID \n" +
            "    JOIN HEALTH_CENTERS hc ON hc.ID  = p2.CENTER_ID \n" +
            "    WHERE NVL(hc.THIRD_LEVEL,0) = :regionCode AND p.IS_REFER = 1 " +
            "AND TO_CHAR(p.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%' " +
            "GROUP BY hc.id",nativeQuery = true)
    List<ReferCenterCount> getReferCenterCount(@Param("regionCode") String regionCode,
                                               @Param("yearMonth") String yearMonth);

    interface CampNo{
        Long getCenterId();
        Integer getCampNo();
        Long getEcId();
        String getEcName();
    }

    @Query(value = "SELECT hc.id as centerId, count(ec.name) AS campNo, ec.id ecId, lower(ec.NAME) ecName FROM HEALTH_CENTERS hc JOIN EVENTS e ON e.CENTER_ID = hc.ID\n" +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID=ec.id  AND ec.IS_SATELLITE = 0\n" +
            "WHERE e.STATUS = 'approved' AND nvl(hc.THIRD_LEVEL,0)=:regionCode GROUP BY hc.id,ec.id, ec.name",nativeQuery = true)
    List<CampNo> getCampNoCenterWiseEvent(@Param("regionCode") String regionCode);

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
            "WHERE e.STATUS = 'approved' AND nvl(hc.THIRD_LEVEL,0)=:regionCode AND pr.id IS NOT NULL AND ec.is_satellite=0\n" +
            "GROUP BY hc.id,ec.id,ec.name, p.id,pr.id) r\n" +
            "GROUP BY r.centerId,r.ecName,r.ecId", nativeQuery = true)
    List<CampWiseCardMemberCount> getCardMemberCenterWiseEvent(@Param("regionCode") String regionCode);


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
            "WHERE e.STATUS = 'approved' AND nvl(hc.THIRD_LEVEL,0)=:regionCode AND pr.id IS NULL AND ec.is_satellite=0\n" +
            "GROUP BY hc.id,ec.id,ec.name, p.id,pr.id) r\n" +
            "GROUP BY r.centerId,r.ecName,r.ecId, r.pid", nativeQuery = true)
    List<CampWiseNonCardMemberCount> getNonCardMemberCenterWiseEvent(@Param("regionCode") String regionCode);


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


    interface SchoolVisitCampStats{
        Integer getTotal();
        Long getCenterId();
    }
    @Query(value = "SELECT count(e.id) as total, CENTER_ID as centerId FROM EVENTS e \n" +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID \n" +
            "JOIN HEALTH_CENTERS hc ON e.CENTER_ID  = hc.ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)  =  :regionCode " +
            "AND lower(ec.NAME) LIKE 'school%' AND to_char(e.EVENT_DATE,'YYYY-MM') = :yearMonth\n" +
            "GROUP BY e.CENTER_ID ",nativeQuery = true)
    List<SchoolVisitCampStats> getSchoolVisitCampNo(
            @Param("regionCode") String regionCode,
            @Param("yearMonth") String yearMonth);



    @Query(value = "SELECT count(pi2.PATIENT_ID) AS total, hc.ID AS centerId FROM EVENTS e \n" +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID \n" +
            "JOIN HEALTH_CENTERS hc ON e.CENTER_ID  = hc.ID \n" +
            "JOIN PATIENT_INVOICES pi2 ON e.id = pi2.EVENT_ID AND pi2.HEALTH_CENTER_ID  = hc.ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.ID \n" +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID AND s.CODE LIKE :serviceName||'%' \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)  =  :regionCode AND lower(ec.NAME) LIKE 'school%' AND to_char(pi2.CREATED_AT,'YYYY-MM') = :yearMonth\n" +
            "GROUP BY hc.ID ", nativeQuery = true)
    List<SchoolVisitCampStats> getSchoolVisitServiceCount(
            @Param("serviceName") String serviceName,
            @Param("regionCode") String regionCode,
            @Param("yearMonth") String yearMonth
            );

    @Query(value = "SELECT count(pi2.PATIENT_ID) AS total, hc.id AS centerId FROM EVENTS e \n" +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID \n" +
            "JOIN HEALTH_CENTERS hc ON e.CENTER_ID  = hc.ID\n" +
            "JOIN PATIENT_INVOICES pi2 ON e.id = pi2.EVENT_ID AND pi2.HEALTH_CENTER_ID  = hc.ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)  = :regionCode AND lower(ec.NAME) LIKE 'school%' " +
            "AND to_char(pi2.CREATED_AT,'YYYY-MM') = :yearMonth\n" +
            "GROUP BY hc.ID",nativeQuery = true)
    List<SchoolVisitCampStats> getSchoolVisitPatientNo(
            @Param("regionCode") String regionCode,
            @Param("yearMonth") String yearMonth);

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

    interface EventCategoryWiseIncomeStats {
        Long getEcId();
        String getEcName();
        String getTotalAmount();
        Long getCenterId();
    }
    @Query(value = "SELECT e.EVENT_CATEGORY_ID ecId,ec.NAME ecName,SUM(pi2.PAID_AMOUNT) totalAmount, pi2.HEALTH_CENTER_ID centerId FROM PATIENT_INVOICES pi2 \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID  = pi2.ID \n" +
            "JOIN events e ON pi2.EVENT_ID  = e.ID \n" +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID  = ec.ID \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID = pi2.HEALTH_CENTER_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,'0') = :regionCode AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%' AND pi2.INVOICE_TYPE = 'camp'\n" +
            "GROUP BY e.EVENT_CATEGORY_ID,ec.NAME , pi2.HEALTH_CENTER_ID",
    nativeQuery = true)
    List<EventCategoryWiseIncomeStats> getCenterCampIncomes(@Param("regionCode") String regionCode,
                                                            @Param("yearMonth") String yearMonth);

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
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode AND lower(sc.NAME) LIKE '%'||:serviceCategory||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%' " +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,s.NAME",nativeQuery = true)
    List<ServiceCategoryIncome> getIncomeByServiceCategory(@Param("regionCode") String regionCode,
                                         @Param("yearMonth") String yearMonth,
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
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode AND lower(s.NAME) LIKE 'free prescription'||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%' AND GET_CURRENT_YEAR(p.CREATED_AT,p.age)>60\n" +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID",nativeQuery = true)
    List<SafetyNetCount> getSafetyNetCount(@Param("regionCode") String regionCode,
                                           @Param("yearMonth") String month
                                           );

    interface DeliveryCount{
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
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode AND lower(s.NAME) LIKE 'delivery'||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%' \n" +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID",nativeQuery = true)
    List<DeliveryCount> getCenterWiseDeliveryCount(@Param("regionCode") String regionCode,
                                                   @Param("yearMonth") String yearMonth);


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
            "    WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode AND lower(sc.NAME) LIKE '%'||'vaccine'||'%'\n" +
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
            "    WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode AND lower(sc.NAME) LIKE '%'||'vaccine'||'%'\n" +
            "    AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') LIKE :yearMonth||'%'\n" +
            "    AND GET_CURRENT_YEAR(p.CREATED_AT,p.age)>18\n" +
            "    GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,s.NAME,p.age",nativeQuery = true)
    List<VaccineCount> getCenterWiseAdultVaccinationCount(@Param("regionCode") String regionCode,
                                                          @Param("yearMonth") String yearMonth
    );


}
