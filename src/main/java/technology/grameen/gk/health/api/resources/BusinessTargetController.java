package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.BusinessTarget;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.requests.BusinessTargetRequest;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.business_target.BusinessTargetService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/business-target")
public class BusinessTargetController {

    private static final Integer PAGE_SIZE = 10;
    private BusinessTargetService businessTargetService;

    public BusinessTargetController(BusinessTargetService businessTargetService) {
        this.businessTargetService = businessTargetService;
    }
    
    @GetMapping("")
    public ResponseEntity<IResponse> getAll(@RequestParam Optional<Integer> page,
                                            @RequestParam Optional<Integer> size,
                                            @RequestParam Optional<String> sortBy,
                                            @RequestParam Optional<Boolean> sortDesc){

        String _sortBy = sortBy.orElse(null);
        _sortBy = (_sortBy.contains("active")) ? "isActive":_sortBy;
        Sort sort = null;

        if(!_sortBy.isEmpty()) {
            sort =   (sortDesc.orElse(false)) ? Sort.by(_sortBy).descending()
                    : Sort.by(_sortBy).ascending();
        }
        Pageable pageable = (sort!=null)? PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE),sort)
                : PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE));

        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                businessTargetService.getAll(pageable)
        ), HttpStatus.OK);
        
    }

    @GetMapping("/detail")
    public ResponseEntity<IResponse> getDetail(@RequestParam String yearMonth,
                                               @RequestParam Long createdOfficeId){

        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                businessTargetService.getAllByYearMonthAndCreatedOffice(yearMonth,createdOfficeId)
        ), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> add(@RequestBody BusinessTargetRequest businessTargetRequest) throws CustomException {



        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                businessTargetService.addBusinessTarget(businessTargetRequest)
        ),
        HttpStatus.OK);
    }
}
