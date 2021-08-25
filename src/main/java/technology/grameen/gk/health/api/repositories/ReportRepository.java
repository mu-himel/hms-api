package technology.grameen.gk.health.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import technology.grameen.gk.health.api.entity.MonthlyStatisticalCenterWiseView;
import technology.grameen.gk.health.api.projection.MonthlyStatisticalReport;

import java.util.List;

public interface ReportRepository extends JpaRepository<MonthlyStatisticalCenterWiseView,Long> {

    @Query(value = "SELECT * FROM TABLE(MSR.GET_MSR_DETAIL_REPORT(:yearMonth,:regionCode))",nativeQuery = true)
    List<MonthlyStatisticalReport> getMonthlyStatisticalReport(@Param("yearMonth") String YearMonth, @Param("regionCode") String regionCode);

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
    @Query(value = "SELECT hc.id as centerId, count(ltg.NAME) campNo, pr.id reg " +
            "FROM HEALTH_CENTERS hc JOIN PATIENT_INVOICES pn ON pn.HEALTH_CENTER_ID = hc.ID " +
            "LEFT JOIN PATIENT_REGISTRATIONS pr ON pr.PATIENT_ID = pn.PATIENT_ID " +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pn.id  " +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID " +
            "JOIN LAB_TEST_GROUPS ltg ON ltg.ID = s.LAB_TEST_GROUP_ID " +
            "WHERE lower(ltg.name) LIKE :labTestGroup || '%' AND hc.THIRD_LEVEL = :regionCode AND pr.id IS NOT NULL " +
            "GROUP BY hc.id, pr.id",nativeQuery = true)
    List<SingleCampNo> getCampNo(@Param("labTestGroup") String labTestGroup, @Param("regionCode") String regionCode);

    interface SingleCardMember{
        Long getCenterId();
        Integer getCardMemberNo();
    }
    @Query(value = "SELECT centerId, count(patientId) cardMemberNo from( " +
            "SELECT hc.id centerId,pr.id, p.id AS patientId, p.id AS pid " +
            "FROM HEALTH_CENTERS hc JOIN PATIENT_INVOICES pn ON pn.HEALTH_CENTER_ID = hc.id " +
            "JOIN patients p ON pn.PATIENT_ID = p.id " +
            "LEFT JOIN PATIENT_REGISTRATIONS pr ON pr.PATIENT_ID = p.id " +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pn.id " +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID " +
            "JOIN LAB_TEST_GROUPS ltg ON ltg.ID = s.LAB_TEST_GROUP_ID " +
            "WHERE lower(ltg.name) LIKE :labTestGroup ||'%' AND hc.THIRD_LEVEL=:regionCode AND pr.id IS NOT NULL " +
            "GROUP BY hc.id,p.id,pr.id) r " +
            "GROUP BY r.centerId",nativeQuery = true)
    List<SingleCardMember> getCardMemberCount(@Param("labTestGroup") String ltg, @Param("regionCode") String regionCode);

    @Query(value = "SELECT centerId, count(patientId) cardMemberNo from( " +
            "SELECT hc.id centerId,pr.id, p.id AS patientId, p.id AS pid " +
            "FROM HEALTH_CENTERS hc JOIN PATIENT_INVOICES pn ON pn.HEALTH_CENTER_ID = hc.id " +
            "JOIN patients p ON pn.PATIENT_ID = p.id " +
            "LEFT JOIN PATIENT_REGISTRATIONS pr ON pr.PATIENT_ID = p.id " +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pn.id " +
            "JOIN SERVICE s ON psd.SERVICE_ID = s.SERVICE_ID " +
            "JOIN LAB_TEST_GROUPS ltg ON ltg.ID = s.LAB_TEST_GROUP_ID " +
            "WHERE lower(ltg.name) LIKE :labTestGroup ||'%' AND hc.THIRD_LEVEL=:regionCode AND pr.id IS NULL " +
            "GROUP BY hc.id,p.id,pr.id) r " +
            "GROUP BY r.centerId",nativeQuery = true)
    List<SingleCardMember> getNonCardMemberCount(@Param("labTestGroup") String usg, @Param("regionCode") String regionCode);
}
