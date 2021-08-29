package technology.grameen.gk.health.api.services.report;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.repositories.ReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolVisitReportServiceImpl implements SchoolVisitReportService{

    private ReportRepository reportRepository;

    public SchoolVisitReportServiceImpl(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    @Override
    public List<ReportRepository.SchoolVisitCampStats> getSchoolVisitCampNo(String regionCode,
                                                                             String yearMonth) {
        return reportRepository.getSchoolVisitCampNo(regionCode, yearMonth);
    }

    @Override
    public List<ReportRepository.SchoolVisitCampStats> getSchoolVisitPatientNo(String regionCode,
                                                                               String yearMonth) {
        return reportRepository.getSchoolVisitPatientNo(regionCode,yearMonth);
    }

    @Override
    public List<ReportRepository.SchoolVisitCampStats> getSchoolVisitServiceCount(String serviceName,
                                                                                      String regionCode,
                                                                                      String yearMonth) {
        return reportRepository.getSchoolVisitServiceCount(serviceName,regionCode,yearMonth);
    }
}
