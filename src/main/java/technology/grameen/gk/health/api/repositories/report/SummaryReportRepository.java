package technology.grameen.gk.health.api.repositories.report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SummaryReportRepository extends ReportRepository{

    interface CampOrganizedStats{
        Long getCenterId();
        Long getByDoctor();
        Long getByParamedic();
        Long getByDmf();
    }



    @Query(value = "SELECT hc.id centerId,(SELECT count(e2.id) byDoctor FROM events e\n" +
            "JOIN EVENT_PERSONNEL ep ON e.ID  = ep.EVENT_ID \n" +
            "JOIN EMPLOYEES e2 ON e2.ID = ep.EMPLOYEE_ID \n" +
            "WHERE e.CENTER_ID = hc.id AND ep.PERSONNEL_TYPE = 'main' AND e2.\"ROLE\" = 'Doctor' " +
            "AND TO_CHAR(e.EVENT_DATE,'YYYY-MM') = :yearMonth ) byDoctor,\n" +
            "(SELECT count(e2.id) byDoctor FROM events e\n" +
            "JOIN EVENT_PERSONNEL ep ON e.ID  = ep.EVENT_ID \n" +
            "JOIN EMPLOYEES e2 ON e2.ID = ep.EMPLOYEE_ID \n" +
            "WHERE e.CENTER_ID = hc.id AND ep.PERSONNEL_TYPE = 'main' AND e2.\"ROLE\" = 'DMF' " +
            "AND TO_CHAR(e.EVENT_DATE,'YYYY-MM') = :yearMonth) byDMF,\n" +
            "(SELECT count(e2.id) byDoctor FROM events e\n" +
            "JOIN EVENT_PERSONNEL ep ON e.ID  = ep.EVENT_ID \n" +
            "JOIN EMPLOYEES e2 ON e2.ID = ep.EMPLOYEE_ID \n" +
            "WHERE e.CENTER_ID = hc.id AND ep.PERSONNEL_TYPE = 'main' AND e2.\"ROLE\" = 'Paramedic' " +
            "AND TO_CHAR(e.EVENT_DATE,'YYYY-MM') = :yearMonth) byParamedic\n" +
            "FROM HEALTH_CENTERS hc \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode", nativeQuery = true)
    List<CampOrganizedStats> getCampOrganizedMonthlyStats(@Param("yearMonth") String yearMonth,
                                                          @Param("regionCode") String regionCode);



    @Query(value = "SELECT hc.id ,(SELECT count(e2.id) byDoctor FROM events e\n" +
            "JOIN EVENT_PERSONNEL ep ON e.ID  = ep.EVENT_ID \n" +
            "JOIN EMPLOYEES e2 ON e2.ID = ep.EMPLOYEE_ID \n" +
            "WHERE e.CENTER_ID = hc.id AND ep.PERSONNEL_TYPE = 'main' AND e2.\"ROLE\" = 'Doctor' " +
            "AND e.EVENT_DATE BETWEEN TO_DATE(:startDate,'YYYY-MM-DD') AND TO_DATE(:endDate,'YYYY-MM-DD')) byDoctor,\n" +
            "(SELECT count(e2.id) byDoctor FROM events e\n" +
            "JOIN EVENT_PERSONNEL ep ON e.ID  = ep.EVENT_ID \n" +
            "JOIN EMPLOYEES e2 ON e2.ID = ep.EMPLOYEE_ID \n" +
            "WHERE e.CENTER_ID = hc.id AND ep.PERSONNEL_TYPE = 'main' AND e2.\"ROLE\" = 'DMF' " +
            "AND e.EVENT_DATE BETWEEN TO_DATE(:startDate,'YYYY-MM-DD') AND TO_DATE(:endDate,'YYYY-MM-DD')) byDMF,\n" +
            "(SELECT count(e2.id) byDoctor FROM events e\n" +
            "JOIN EVENT_PERSONNEL ep ON e.ID  = ep.EVENT_ID \n" +
            "JOIN EMPLOYEES e2 ON e2.ID = ep.EMPLOYEE_ID \n" +
            "WHERE e.CENTER_ID = hc.id AND ep.PERSONNEL_TYPE = 'main' AND e2.\"ROLE\" = 'Paramedic' " +
            "AND e.EVENT_DATE BETWEEN TO_DATE(:startDate,'YYYY-MM-DD') AND TO_DATE(:endDate,'YYYY-MM-DD')) byParamedic\n" +
            "FROM HEALTH_CENTERS hc \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode", nativeQuery = true)
    List<CampOrganizedStats> getCampOrganizedRangeStats(@Param("startDate") String startDate,
                                                          @Param("endDate") String endDate,
                                                          @Param("regionCode") String regionCode);


    interface VaccineCount{
        Long getTotal();
        Long getCenterId();
        Long getServiceId();
        String getServiceName();
    }
    @Query(value = "SELECT NVL(count(pi2.id),0) as total," +
            "pi2.HEALTH_CENTER_ID as centerId, s.SERVICE_ID as serviceId,s.NAME as serviceName FROM patient_invoices pi2\n" +
            "JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode AND sc.alias LIKE '%'||:serviceCategory||'%'\n" +
            "AND TO_CHAR(pi2.CREATED_AT,'YYYY-MM') = :yearMonth " +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,s.NAME",nativeQuery = true)
    List<VaccineCount> getSummaryOfServiceCount(@Param("regionCode") String regionCode,
                                                           @Param("yearMonth") String yearMonth,
                                                           @Param("serviceCategory") String serviceCategory);

    @Query(value = "SELECT NVL(count(pi2.id),0) as total," +
            "pi2.HEALTH_CENTER_ID as centerId, s.SERVICE_ID as serviceId,s.NAME as serviceName FROM patient_invoices pi2\n" +
            "JOIN HEALTH_CENTERS hc ON pi2.HEALTH_CENTER_ID = hc.ID \n" +
            "JOIN PATIENT_SERVICE_DETAILS psd ON psd.PATIENT_INVOICE_ID = pi2.id \n" +
            "JOIN SERVICE s ON s.SERVICE_ID  = psd.SERVICE_ID \n" +
            "JOIN SERVICE_CATEGORIES sc ON sc.ID = s.SERVICE_CATEGORY_ID \n" +
            "WHERE NVL(hc.THIRD_LEVEL,0)=:regionCode AND sc.alias LIKE '%'||:serviceCategory||'%'\n" +
            "AND pi2.CREATED_AT BETWEEN TO_DATE(:startDate,'YYYY-MM-DD') AND TO_DATE(:endDate,'YYYY-MM-DD') " +
            "GROUP BY pi2.HEALTH_CENTER_ID , s.SERVICE_ID,s.NAME",nativeQuery = true)
    List<VaccineCount> getSummaryOfServiceCount(@Param("regionCode") String regionCode,
                                                @Param("startDate") String startDate,
                                                @Param("endDate") String endDate,
                                                @Param("serviceCategory") String serviceCategory);

}
