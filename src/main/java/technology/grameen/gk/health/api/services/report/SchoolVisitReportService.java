package technology.grameen.gk.health.api.services.report;

import technology.grameen.gk.health.api.repositories.ReportRepository;

import java.util.List;

public interface SchoolVisitReportService {
    public List<ReportRepository.SchoolVisitCampStats> getSchoolVisitCampNo(String regionCode,
                                                                            String yearMonth);
    public List<ReportRepository.SchoolVisitCampStats> getSchoolVisitPatientNo(String regionCode,
                                                     String yearMonth);
    public List<ReportRepository.SchoolVisitCampStats> getSchoolVisitServiceCount(String serviceName,String regionCode,
                                                        String yearMonth);
}
