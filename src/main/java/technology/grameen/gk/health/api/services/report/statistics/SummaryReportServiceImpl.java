package technology.grameen.gk.health.api.services.report.statistics;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.repositories.report.SummaryReportRepository;
import technology.grameen.gk.health.api.services.report.ReportService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SummaryReportServiceImpl implements SummaryReportService{

    private SummaryReportRepository summaryReportRepository;

    public SummaryReportServiceImpl(SummaryReportRepository summaryReportRepository) {
        this.summaryReportRepository = summaryReportRepository;
    }

    @Override
    public Optional<?> getSummaryCampOrganizedStats(String type, String startDate, String endDate, String regionCode) {
        if(type.equalsIgnoreCase("monthly")) {
            return Optional
                    .ofNullable(summaryReportRepository
                            .getCampOrganizedMonthlyStats(startDate, regionCode));
        }else if(type.equalsIgnoreCase("range") ||
                type.equalsIgnoreCase("daily")){
            return Optional
                    .ofNullable(summaryReportRepository
                            .getCampOrganizedRangeStats(startDate,endDate,regionCode));
        }

        return Optional.empty();
    }

    @Override
    public Optional<?> getSummaryVaccineStats(String type, String startDate, String endDate, String regionCode) {
        if(type.equalsIgnoreCase("monthly")) {
            return Optional
                    .ofNullable(summaryReportRepository
                            .getSummaryOfServiceCount(regionCode,startDate,"vaccine"));
        }else if(type.equalsIgnoreCase("range") ||
                type.equalsIgnoreCase("daily")){
            return Optional
                    .ofNullable(summaryReportRepository
                            .getSummaryOfServiceCount(regionCode,startDate,endDate,"vaccine"));
        }

        return Optional.empty();
    }

    @Override
    public Optional<?> getSummarySurgeryStats(String type, String startDate, String endDate, String regionCode) {
        if(type.equalsIgnoreCase("monthly")) {
            return Optional
                    .ofNullable(summaryReportRepository
                            .getSummaryOfServiceCount(regionCode,startDate,"surgery"));
        }else if(type.equalsIgnoreCase("range") ||
                type.equalsIgnoreCase("daily")){
            return Optional
                    .ofNullable(summaryReportRepository
                            .getSummaryOfServiceCount(regionCode,startDate,endDate,"surgery"));
        }
        return Optional.empty();
    }

    @Override
    public Optional<?> getSummaryUltrasonoStats(String type, String startDate, String endDate, String regionCode) {
        if(type.equalsIgnoreCase("monthly")) {
            return Optional
                    .ofNullable(summaryReportRepository
                            .getSummaryOfServiceCount(regionCode,startDate,"ultrasono"));
        }else if(type.equalsIgnoreCase("range") ||
                type.equalsIgnoreCase("daily")){
            return Optional
                    .ofNullable(summaryReportRepository
                            .getSummaryOfServiceCount(regionCode,startDate,endDate,"ultrasono"));
        }
        return Optional.empty();
    }

    @Override
    public Optional<?> getSummaryEcgStats(String type, String startDate, String endDate, String regionCode) {
        if(type.equalsIgnoreCase("monthly")) {
            return Optional
                    .ofNullable(summaryReportRepository
                            .getSummaryOfServiceCount(regionCode,startDate,"ecg"));
        }else if(type.equalsIgnoreCase("range") ||
                type.equalsIgnoreCase("daily")){
            return Optional
                    .ofNullable(summaryReportRepository
                            .getSummaryOfServiceCount(regionCode,startDate,endDate,"ecg"));
        }
        return Optional.empty();
    }

    @Override
    public Optional<?> getSummaryXrayStats(String type, String startDate, String endDate, String regionCode) {
        if(type.equalsIgnoreCase("monthly")) {
            return Optional
                    .ofNullable(summaryReportRepository
                            .getSummaryOfServiceCount(regionCode,startDate,"x-ray"));
        }else if(type.equalsIgnoreCase("range") ||
                type.equalsIgnoreCase("daily")){
            return Optional
                    .ofNullable(summaryReportRepository
                            .getSummaryOfServiceCount(regionCode,startDate,endDate,"x-ray"));
        }
        return Optional.empty();
    }
}
