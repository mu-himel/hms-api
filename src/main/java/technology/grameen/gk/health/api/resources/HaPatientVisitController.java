package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.HaPatientVisitLog;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.ExceptionResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.HaPatientVisitLogService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ha-patient-visit")
public class HaPatientVisitController {

    private static final Integer PAGE_SIZE = 10;
    private HaPatientVisitLogService patientVisitLogService;

    public HaPatientVisitController(HaPatientVisitLogService patientVisitLogService) {
        this.patientVisitLogService = patientVisitLogService;
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> addPatientVisit(@RequestBody HaPatientVisitLog patientVisitLog){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                patientVisitLogService.addPatientVisit(patientVisitLog)
        ), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<IResponse> getVouchers(@RequestParam Optional<Integer> page,
                                                 @RequestParam Optional<Integer> size,
                                                 @RequestParam Optional<String> sortBy,
                                                 @RequestParam Optional<Boolean> sortDesc){


        Pageable pageable = null;
        try{
            page.orElseThrow(()-> new Exception("Query param page missing"));
            size.orElseThrow(()-> new Exception("Query param size missing"));
            sortBy.orElseThrow(()-> new Exception("Query param sortBy missing"));
            sortDesc.orElseThrow(()-> new Exception("Query param sortDesc missing"));

            String _sortBy = sortBy.orElse("id");
            _sortBy = (_sortBy.contains("active"))? "isActive" : _sortBy;

            Sort sort = null;
            if(!_sortBy.isEmpty()){
                sort = (sortDesc.orElse(false)) ? Sort.by(_sortBy).descending() :
                        Sort.by(_sortBy).ascending();
            }

            pageable = (sort!=null)? PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE),sort)
                    : PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE));

        }catch (Exception ex){
            return new ResponseEntity<>(new ExceptionResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),ex.getMessage()),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                patientVisitLogService.getVisitLogs(pageable)
        ), HttpStatus.OK);

    }
}
