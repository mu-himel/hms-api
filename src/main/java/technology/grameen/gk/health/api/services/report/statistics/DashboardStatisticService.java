package technology.grameen.gk.health.api.services.report.statistics;

import java.util.Map;

public interface DashboardStatisticService {




    Map<String,Object> getPatientRegistrationStats(String regionCode, String centerCode,String type,
                                                   String fromDate, String toDate);



    Map<String,?> getServiceSaleStats(String regionCode, String centerCode,String type,
                                      String fromDate, String toDate);


    Map<String,Object> getPatientVisitStats(String regionCode, String centerCode, String type,
                                String fromDate, String toDate);
}
