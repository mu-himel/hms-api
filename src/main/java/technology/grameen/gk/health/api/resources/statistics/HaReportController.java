package technology.grameen.gk.health.api.resources.statistics;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.report.statistics.HaReportService;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/ha-report")
public class HaReportController {

    private HaReportService haReportService;

    public HaReportController(HaReportService haReportService) {
        this.haReportService = haReportService;
    }

    @GetMapping("/monthly")
    public ResponseEntity<IResponse> getChaReport(@RequestParam Optional<String> month){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
            haReportService.getChaReportByMonth(month.orElse(""))
        ), HttpStatus.OK);
    }
}
