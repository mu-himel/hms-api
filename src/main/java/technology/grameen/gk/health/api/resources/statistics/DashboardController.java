package technology.grameen.gk.health.api.resources.statistics;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.report.statistics.DashboardStatisticService;
import technology.grameen.gk.health.api.services.report.statistics.SummaryReportService;


@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    DashboardStatisticService statisticService;
    private SummaryReportService summaryReportService;
    public DashboardController(DashboardStatisticService statisticService,
                               SummaryReportService summaryReportService
                               ) {
        this.statisticService = statisticService;
        this.summaryReportService = summaryReportService;

    }

    @GetMapping("/patient-reg")
    public ResponseEntity<IResponse> getPatientRegStatistics(@RequestParam String regionCode,
                                                               @RequestParam String centerCode,
                                                               @RequestParam String type,
                                                               @RequestParam String fromDate,
                                                               @RequestParam String toDate){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                statisticService.getPatientRegistrationStats(regionCode,centerCode,type,fromDate,toDate)
        ), HttpStatus.OK);
    }

    @GetMapping("/patient-visit")
    public ResponseEntity<IResponse> getPatientVisitStatistics(@RequestParam String regionCode,
                                                               @RequestParam String centerCode,
                                                               @RequestParam String type,
                                                               @RequestParam String fromDate,
                                                               @RequestParam String toDate){

        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                statisticService.getPatientVisitStats(regionCode,centerCode,type,fromDate,toDate)
        ), HttpStatus.OK);
    }

    @GetMapping("/service-stats")
    public ResponseEntity<IResponse> getServiceStatistics(@RequestParam String regionCode,
                                                          @RequestParam String centerCode,
                                                          @RequestParam String type,
                                                          @RequestParam String fromDate,
                                                          @RequestParam String toDate){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                statisticService.getServiceSaleStats(regionCode,centerCode,type,fromDate,toDate)
        ), HttpStatus.OK);
    }


    @GetMapping("/camp-stats")
    public ResponseEntity<IResponse> getCampStatistics(@RequestParam String regionCode,
                                                          @RequestParam String centerCode,
                                                          @RequestParam String type,
                                                          @RequestParam String fromDate,
                                                          @RequestParam String toDate){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                summaryReportService.getSummaryCampStats(type,fromDate,toDate,regionCode,centerCode)
        ), HttpStatus.OK);
    }
}
