package technology.grameen.gk.health.api.resources;

import org.keycloak.authorization.client.util.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.requests.ServiceRecordSearch;
import technology.grameen.gk.health.api.requests.StatsRequest;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.responses.StatsResponse;
import technology.grameen.gk.health.api.services.report.ReportService;
import technology.grameen.gk.health.api.services.report.statistics.SummaryReportService;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private  ReportService reportService;
    private SummaryReportService summaryReportService;

    ReportController(ReportService reportService,
                     SummaryReportService summaryReportService){
        this.reportService = reportService;
        this.summaryReportService = summaryReportService;
    }
    @GetMapping("/service-records")
    public ResponseEntity<IResponse> getServiceRecords(){
        return  new ResponseEntity<>(new EntityCollectionResponse<>(HttpStatus.OK.value(),
                reportService.getPatientInvoiceSummery()),HttpStatus.OK);
    }

    @PostMapping("/service-records")
    public ResponseEntity<IResponse> getServiceRecords(@RequestBody ServiceRecordSearch serviceRecordSearch){
        return  new ResponseEntity<>(new EntityCollectionResponse<>(HttpStatus.OK.value(),
                reportService.getPatientInvoiceSummery(serviceRecordSearch)),HttpStatus.OK);
    }

    @PostMapping("/stats")
    public ResponseEntity<IResponse> getStats(@RequestBody StatsRequest req){
        HealthCenter healthCenter = new HealthCenter();
        healthCenter.setId(req.getId());
        healthCenter.setOfficeTypeId(req.getOfficeTypeId());
        healthCenter.setCenterCode(req.getCenterCode());

        Optional<BigDecimal> totalAmountUptoLastDay = reportService.getPatientService()
                .getTotalAmountUptoLastDay(healthCenter,req.getToDate());

        Optional<BigDecimal> totalAmount = reportService.getPatientService().getTotalAmount(healthCenter,
                req.getFromDate(),req.getToDate());

        Integer totalPatientUpToLastDay = reportService.getPatientService()
                .getAllPatientCountUpToLastDay(healthCenter, req.getToDate());

        Integer totalPatient = reportService.getPatientService().getAllPatientCount(healthCenter,
                req.getFromDate(),req.getToDate());

        Integer totalGbPatientUpToLastDay = reportService.getPatientService()
                .getGbPatientCountUpToLastDay(healthCenter,req.getToDate());

        Integer totalGbPatient = reportService.getPatientService().getGbPatientCount(healthCenter,
                req.getFromDate(), req.getToDate());

        return new ResponseEntity<>(new StatsResponse(
                totalPatient,
                totalGbPatient,
                (totalPatient-totalGbPatient),
                totalAmount.orElse(BigDecimal.valueOf(0)),
                totalAmountUptoLastDay.orElse(BigDecimal.valueOf(0)),
                totalPatientUpToLastDay,
                totalGbPatientUpToLastDay,
                (totalPatientUpToLastDay-totalGbPatientUpToLastDay)
                ),HttpStatus.OK);
    }

    @GetMapping("/month-wise-received")
    public ResponseEntity<IResponse> getMonthWiseTotalAmountReceived(@RequestParam Optional<Long> centerId){
        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),
                reportService.getMonthWiseTotalAmountReceived(centerId.orElse(Long.valueOf(0)))),HttpStatus.OK);
    }

    @GetMapping("/event-schedule")
    public ResponseEntity<IResponse> getEventSchedule(@RequestParam Optional<String> raThirdLevelCode,
                                                      @RequestParam Optional<String> yearMonth) throws CustomException {
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                reportService.getEventSchedule(raThirdLevelCode.orElse(null),yearMonth.orElse(null))
        ), HttpStatus.OK);
    }

    @GetMapping("/satellite-schedule")
    public ResponseEntity<IResponse> getSatelliteSchedule(@RequestParam Optional<String> raThirdLevelCode,
                                                          @RequestParam Optional<String> yearMonth) throws CustomException {
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                reportService.getSatteliteSchedule(raThirdLevelCode.orElse(""),
                        yearMonth.orElse(""))
        ), HttpStatus.OK);
    }

    @GetMapping("/monthly-statistical-report")
    public ResponseEntity<IResponse> getMonthlyDetailReport(@RequestParam Optional<String> regionCode,
                                                            @RequestParam Optional<String> month){

        return new ResponseEntity<>(
                new EntityResponse<>(
                        HttpStatus.OK.value(),
                        reportService.getMonthlyStatisticalReport(month.orElse(""),regionCode.orElse(""))
                ), HttpStatus.OK
        );
    }

    @GetMapping("/range-statistics")
    public ResponseEntity<IResponse> getRangeDetailReport(@RequestParam Optional<String> startDate,
                                                          @RequestParam Optional<String> endDate,
                                                          @RequestParam Optional<String> regionCode){
        return new ResponseEntity<>(
                new EntityResponse<>(
                        HttpStatus.OK.value(),
                        reportService.getRangeStatisticalReport(startDate.orElse(null),
                                endDate.orElse(null),regionCode.orElse(null))
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/lab-report")
    public ResponseEntity<IResponse> getLabReport(@RequestParam Optional<String> regionCode,
                                                         @RequestParam Optional<String> type,
                                                  @RequestParam Optional<String> dateTime){

        return new ResponseEntity<>(
                new EntityResponse<>(
                        HttpStatus.OK.value(),
                        reportService.getLabTestReport(regionCode.orElse(""),
                                type.orElse(""),
                                dateTime.orElse(""))
                ),
                HttpStatus.OK
        );

    }

    @GetMapping("/summary-stats")
    public ResponseEntity<IResponse> getSummaryStates(@RequestParam Optional<String> type,
                                                      @RequestParam Optional<String> startDate,
                                                      @RequestParam Optional<String> endDate,
                                                      @RequestParam Optional<String> regionCode){

        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                reportService.getSummaryReportStats(type.orElse(null),startDate.orElse(""),
                        endDate.orElse(""),regionCode.orElse(""))
        ), HttpStatus.OK);

    }

    @GetMapping(value = "/board-member-report")
    public ResponseEntity<IResponse> getBoardReport(@RequestParam String fromMonth,
                                                    @RequestParam String toMonth){

        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                summaryReportService.getBoardMemberReport(fromMonth,toMonth)
        ), HttpStatus.OK);
    }

    @GetMapping(value = "/delivery-report")
    public ResponseEntity<IResponse> getDeliveryReport(@RequestParam String lastMonth,
                                                       @RequestParam String currentMonth,
                                                       @RequestParam Optional<String> regionCode){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                reportService.getMonthWiseDeliveryReport(lastMonth,currentMonth,regionCode.orElse(""))
        ), HttpStatus.OK);
    }
}
