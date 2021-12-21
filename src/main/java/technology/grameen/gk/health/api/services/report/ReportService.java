package technology.grameen.gk.health.api.services.report;

import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.projection.MonthWiseReceived;
import technology.grameen.gk.health.api.projection.ServiceRecord;
import technology.grameen.gk.health.api.projection.event.schedule.HCenter;
import technology.grameen.gk.health.api.repositories.EventRepository;
import technology.grameen.gk.health.api.repositories.report.LabTestReportRepository;
import technology.grameen.gk.health.api.requests.ServiceRecordSearch;
import technology.grameen.gk.health.api.responses.ServiceRecordResponse;
import technology.grameen.gk.health.api.services.patient.PatientManageService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReportService {

     List<ServiceRecord>  getPatientInvoiceSummery();

     List<ServiceRecordResponse> getPatientInvoiceSummery(ServiceRecordSearch serviceRecordSearch);

     PatientManageService getPatientService();

     MonthWiseReceived getMonthWiseTotalAmountReceived(Long centerId);

    List<HCenter> getEventSchedule(String raCode, String yearMonth) throws CustomException;

    List<EventRepository.EventSchedule> getSatteliteSchedule(String orElse, String orElse1) throws CustomException;

    Map<String, Object> getMonthlyStatisticalReport(String yearMonth,String regionCode);

    List<LabTestReportRepository.MonthlyLabTestReport> getLabTestReport(String regionCode, String yearMonth,
                                                                        String dateTime);

    Map<?,?> getSummaryReportStats(String type, String startDate ,String endDate, String regionCode);

    Map<?,?> getRangeStatisticalReport(String startDate, String endDate, String regionCode);
}
