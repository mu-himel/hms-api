package technology.grameen.gk.health.api.services.report;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.repositories.report.ReportRepository;

import java.util.List;

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
    public List<ReportRepository.SchoolVisitCampStats> getSchoolVisitCampNo(String regionCode, String sdt, String edt) {
        return reportRepository.getSchoolVisitCampNo(regionCode, sdt,edt);
    }

    @Override
    public List<ReportRepository.SchoolVisitCampStats> getSchoolVisitPatientNo(String regionCode,
                                                                               String yearMonth) {
        return reportRepository.getSchoolVisitPatientNo(regionCode,yearMonth);
    }

    @Override
    public List<ReportRepository.SchoolVisitCampStats> getSchoolVisitPatientNo(String regionCode,
                                                                               String sdt, String edt) {
        return reportRepository.getSchoolVisitPatientNo(regionCode,sdt, edt);
    }

    @Override
    public List<ReportRepository.SchoolVisitCampStats> getSchoolVisitServiceCount(String serviceName,
                                                                                      String regionCode,
                                                                                      String yearMonth) {
        return reportRepository.getSchoolVisitServiceCount(serviceName,regionCode,yearMonth);
    }

    @Override
    public List<ReportRepository.SchoolVisitCampStats> getSchoolVisitServiceCount(String serviceName,
                                                                                  String regionCode,
                                                                                  String sdt, String edt) {
        return reportRepository.getSchoolVisitServiceCount(serviceName,regionCode,sdt, edt);
    }
}
