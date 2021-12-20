package technology.grameen.gk.health.api.services.report.statistics;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.repositories.report.BoardMemberReportRepository;
import technology.grameen.gk.health.api.repositories.report.ReportRepository;
import technology.grameen.gk.health.api.repositories.report.SummaryReportRepository;
import technology.grameen.gk.health.api.services.report.ReportService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SummaryReportServiceImpl implements SummaryReportService{

    private SummaryReportRepository summaryReportRepository;
    private ReportRepository reportRepository;
    private  BoardMemberReportRepository boardMemberReportRepository;

    public SummaryReportServiceImpl(SummaryReportRepository summaryReportRepository,
                                    BoardMemberReportRepository boardMemberReportRepository,
                                    ReportRepository reportRepository) {
        this.summaryReportRepository = summaryReportRepository;
        this.boardMemberReportRepository = boardMemberReportRepository;
        this.reportRepository = reportRepository;
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

    @Override
    public Optional<?> getSummaryCampStats(String type, String startDate, String endDate,
                                           String regionCode, String centerCode) {

        if(type.equalsIgnoreCase("monthly")) {
            if(!regionCode.isEmpty() && centerCode.isEmpty()) {
                return Optional
                        .ofNullable(reportRepository.getCenterCampIncomes(regionCode, startDate));
            }else if((regionCode.isEmpty() || !regionCode.isEmpty()) && (!centerCode.isEmpty())){
                return Optional.ofNullable(reportRepository.getCenterCampIncomesByCenter(centerCode,startDate));
            } else{
                return Optional
                        .ofNullable(reportRepository.getCenterCampIncomesFromHO(startDate));
            }
        }else if(type.equalsIgnoreCase("range") ||
                type.equalsIgnoreCase("daily")){
            LocalDateTime _startDate = LocalDateTime.parse(startDate);
            LocalDateTime _endDate = LocalDateTime.parse(endDate);

            if(!regionCode.isEmpty() && centerCode.isEmpty()){

                return Optional
                        .ofNullable(reportRepository.getCenterCampIncomesByRange(regionCode, _startDate, _endDate));

            }else if((regionCode.isEmpty() || !regionCode.isEmpty()) && (!centerCode.isEmpty())) {
                return Optional
                        .ofNullable(reportRepository.getCenterCampIncomesByRangeByCenter(centerCode, _startDate, _endDate));
            }else{
                return Optional
                        .ofNullable(reportRepository.getCenterCampIncomesByRangeFromHo(_startDate, _endDate));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<?> getBoardMemberReport(String fromMonth, String toMonth) {
        Map<String,Object> map = new HashMap<>();
        map.put("region",boardMemberReportRepository.totalRegion());
        map.put("center",boardMemberReportRepository.totalCenter());
        map.put("patientTreatedAtHc",boardMemberReportRepository.getPatientTreadByHc(fromMonth,toMonth));
        map.put("patientTreatedAtSat",boardMemberReportRepository.getPatientTreadBySat(fromMonth,toMonth));
        map.put("satCampNo",boardMemberReportRepository.getSatCampNo(fromMonth,toMonth));
        map.put("noOfVariousCamp",boardMemberReportRepository.getNoOfVariousCamp(fromMonth,toMonth));
        map.put("patientTreatedAtVariousCamp",boardMemberReportRepository.getPatientTreatedAtVariousCamp(fromMonth,toMonth));
        map.put("vaccineCount",boardMemberReportRepository.getServiceCount("vaccine",fromMonth,toMonth));
        map.put("houseVisited",boardMemberReportRepository.getHouseVisit(fromMonth,toMonth));
        map.put("patientCheckup",boardMemberReportRepository.getPatientCheckup(fromMonth,toMonth));
        map.put("pregnantRegistered",boardMemberReportRepository.getPregnantRegistered(fromMonth,toMonth));
        map.put("deliveryPerformed",boardMemberReportRepository.getDeliveryPerformed(fromMonth,toMonth));
        map.put("diabeticCheckup",boardMemberReportRepository.getDiabeticCheckup(fromMonth,toMonth));

        return Optional.of(map);
    }
}
