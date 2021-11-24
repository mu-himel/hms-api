package technology.grameen.gk.health.api.repositories.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusinessTargetStatRepository extends ReportRepository{

    interface BusinessStats{
        Double getHcIncomePerDay();
        Long getHcPatientPerDay();
        Double getSatIncomePerDay();
        Long getSatPatientPerDay();
        Long getHcPatientCount();
        Double getHcAmount();
        Long getHcId();
        Long getSatPatientCount();
        Double getSatAmount();
        Long getSatCenterId();

    }
    @Query(value = "SELECT \n" +
            "hc_income_per_day hcIncomePerDay,hc_patient_per_day hcPatientPerDay,sat_income_per_day satIncomePerDay,\n" +
            "sat_patient_per_day satPatientPerDay,\n" +
            "nvl(t2.hcPatientCount,0) hcPatientCount,nvl(t2.hcAmount,0) hcAmount,t2.health_center_id hcId,\n" +
            "nvl(t3.satPatientCount,0) satPatientCount,nvl(t3.satAmount,0) satAmount,t3.health_center_id satCenterId FROM (SELECT * FROM BUSINESS_TARGETS bt \n" +
            "WHERE bt.YEAR_MONTH  = :yearMonth\n" +
            "AND bt.FOR_OFFICE_ID = :centerId) t1\n" +
            "LEFT JOIN \n" +
            "(SELECT count(pi2.patient_id) hcPatientCount, sum(pi2.PAID_AMOUNT) hcAmount,pi2.HEALTH_CENTER_ID FROM PATIENT_INVOICES pi2\n" +
            "WHERE pi2.HEALTH_CENTER_ID = :centerId AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') = :fromDate AND pi2.EVENT_ID  IS NULL GROUP BY pi2.HEALTH_CENTER_ID) t2\n" +
            "ON t1.for_office_id = t2.HEALTH_CENTER_ID\n" +
            "LEFT JOIN (SELECT count(pi3.patient_id) satPatientCount, sum(pi3.PAID_AMOUNT) satAmount,pi3.HEALTH_CENTER_ID FROM PATIENT_INVOICES pi3\n" +
            "WHERE pi3.HEALTH_CENTER_ID = :centerId AND TO_CHAR(pi3.CREATED_AT,'YYYY-MM-DD') = :fromDate AND pi3.EVENT_ID  IS NOT NULL AND pi3.INVOICE_TYPE='satellite'\n" +
            "\tGROUP BY pi3.HEALTH_CENTER_ID) t3\n" +
            "ON t1.for_office_id = t3.HEALTH_CENTER_ID",nativeQuery = true)
    List<BusinessStats> getCenterStats(@Param("centerId") Long centerId, @Param("yearMonth") String yearMonth,
                           @Param("fromDate") String fromDate);

    @Query(value = "SELECT \n" +
            "t1.FOR_OFFICE_ID forOfficeID, hc_income_per_day hcIncomePerDay,hc_patient_per_day hcPatientPerDay,sat_income_per_day satIncomePerDay,\n" +
            "sat_patient_per_day satPatientPerDay,\n" +
            "nvl(t2.hcPatientCount,0) hcPatientCount,nvl(t2.hcAmount,0) hcAmount,t2.health_center_id hcId,\n" +
            "nvl(t3.satPatientCount,0) satPatientCount,nvl(t3.satAmount,0) satAmount,t3.health_center_id satCenterId FROM (SELECT * FROM BUSINESS_TARGETS bt \n" +
            "WHERE bt.YEAR_MONTH  = :yearMonth AND FOR_OFFICE_ID = :regionId) t1\n" +
            "LEFT JOIN \n" +
            "(SELECT :regionId AS areaid, count(pi2.patient_id) hcPatientCount, sum(pi2.PAID_AMOUNT) hcAmount,pi2.HEALTH_CENTER_ID FROM PATIENT_INVOICES pi2\n" +
            "WHERE pi2.HEALTH_CENTER_ID IN (SELECT id FROM HEALTH_CENTERS hc2 WHERE THIRD_LEVEL IN (\n" +
            "SELECT THIRD_LEVEL FROM HEALTH_CENTERS hc WHERE id = :regionId) AND OFFICE_TYPE_ID = 6) \n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM-DD') = :fromDate AND pi2.EVENT_ID  IS NULL GROUP BY pi2.HEALTH_CENTER_ID) t2\n" +
            "ON t1.for_office_id = t2.areaid\n" +
            "LEFT JOIN (SELECT :regionId AS areaid, count(pi3.patient_id) satPatientCount, sum(pi3.PAID_AMOUNT) satAmount,pi3.HEALTH_CENTER_ID FROM PATIENT_INVOICES pi3\n" +
            "WHERE pi3.HEALTH_CENTER_ID IN (SELECT id FROM HEALTH_CENTERS hc2 WHERE THIRD_LEVEL IN (\n" +
            "SELECT THIRD_LEVEL FROM HEALTH_CENTERS hc WHERE id = :regionId) AND OFFICE_TYPE_ID = 6) " +
            "AND TO_CHAR(pi3.CREATED_AT,'YYYY-MM-DD') = :fromDate AND pi3.EVENT_ID  IS NOT NULL AND pi3.INVOICE_TYPE='satellite'\n" +
            "\tGROUP BY pi3.HEALTH_CENTER_ID) t3\n" +
            "ON t1.for_office_id = t3.areaid",nativeQuery = true)
    List<BusinessStats> getRegionStats(@Param("regionId") Long regionId, @Param("yearMonth") String yearMonth,
                           @Param("fromDate") String fromDate);
}
