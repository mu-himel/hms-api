package technology.grameen.gk.health.api.services.report.statistics;

import java.util.Map;

public interface DashboardStatisticService {




    Map<String,Object> getPatientRegistrationStats(String regionCode, String centerCode,String type,
                                                   String fromDate, String toDate);



    Map<String,?> getDailyServiceSale(String regionCode, String centerCode);
    Map<String,?> getMonthlyServiceSale(String regionCode, String centerCode,String monthYear);
    Map<String,?> getRangeServiceSale(String regionCode, String centerCode,String fromDate, String toDate);

    Map<String,Object> getPatientVisitStats(String regionCode, String centerCode, String type,
                                String fromDate, String toDate);
}
