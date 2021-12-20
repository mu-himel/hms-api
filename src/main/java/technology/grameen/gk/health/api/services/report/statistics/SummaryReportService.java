package technology.grameen.gk.health.api.services.report.statistics;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SummaryReportService {

    Optional<?> getSummaryCampOrganizedStats(String type, String startDate, String endDate, String regionCode);
    Optional<?> getSummaryVaccineStats(String type, String startDate, String endDate, String regionCode);
    Optional<?> getSummarySurgeryStats(String type, String startDate, String endDate, String regionCode);
    Optional<?> getSummaryUltrasonoStats(String type, String startDate, String endDate, String regionCode);
    Optional<?> getSummaryEcgStats(String type, String startDate, String endDate, String regionCode);
    Optional<?> getSummaryXrayStats(String type, String startDate, String endDate, String regionCode);
    Optional<?> getSummaryCampStats(String type, String startDate, String endDate, String regionCode,String centerCode);

    Optional<?> getBoardMemberReport(String fromMonth, String toMonth);
}
