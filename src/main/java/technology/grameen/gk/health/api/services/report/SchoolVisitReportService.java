package technology.grameen.gk.health.api.services.report;

import technology.grameen.gk.health.api.repositories.report.ReportRepository;

import java.util.List;

public interface SchoolVisitReportService {
    List<ReportRepository.SchoolVisitCampStats> getSchoolVisitCampNo(String regionCode,
                                                                            String yearMonth);

    List<ReportRepository.SchoolVisitCampStats> getSchoolVisitCampNo(String regionCode,
                                                                            String sdt,String edt);

    List<ReportRepository.SchoolVisitCampStats> getSchoolVisitPatientNo(String regionCode,
                                                     String yearMonth);

    List<ReportRepository.SchoolVisitCampStats> getSchoolVisitPatientNo(String regionCode,
                                                                               String sdt, String edt);

    List<ReportRepository.SchoolVisitCampStats> getSchoolVisitServiceCount(String serviceName,String regionCode,
                                                        String yearMonth);

    List<ReportRepository.SchoolVisitCampStats> getSchoolVisitServiceCount(String serviceName,String regionCode,
                                                                                  String sdt, String edt);
}
