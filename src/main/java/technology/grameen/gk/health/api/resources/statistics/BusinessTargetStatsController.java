package technology.grameen.gk.health.api.resources.statistics;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.report.statistics.BusinessTargetStatService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/business-target-stats")
public class BusinessTargetStatsController {

    private BusinessTargetStatService businessTargetStatService;


    public BusinessTargetStatsController(BusinessTargetStatService businessTargetStatService) {
        this.businessTargetStatService = businessTargetStatService;
    }

    @GetMapping("")
    public ResponseEntity<IResponse> getBusinessTargetStats(@RequestParam Optional<Long> regionId,
                                                            @RequestParam Optional<Short> officeTypeId,
                                                            @RequestParam Optional<String> yearMonth,
                                                            @RequestParam Optional<String> fromDate
                                                            ) throws CustomException {
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                businessTargetStatService.getStats(regionId.orElse(null),
                        officeTypeId.orElse(null),
                        yearMonth.orElse(""),
                        fromDate.orElse("")
                        )
        ), HttpStatus.OK);
    }
}
