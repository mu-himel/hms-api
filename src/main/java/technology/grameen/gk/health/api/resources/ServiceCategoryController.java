package technology.grameen.gk.health.api.resources;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.ServiceCategory;
import technology.grameen.gk.health.api.responses.*;
import technology.grameen.gk.health.api.services.ServiceCategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/service-category")
public class ServiceCategoryController {

    private final Integer PAGE_SIZE = 10;

    private ServiceCategoryService serviceCategoryService;

    public ServiceCategoryController(ServiceCategoryService serviceCategoryService){
        this.serviceCategoryService = serviceCategoryService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<IResponse> list(){
        return new ResponseEntity<>(new EntityCollectionResponse<>(HttpStatus.OK.value(),
                serviceCategoryService.getCategories()),HttpStatus.OK);
    }

    @RequestMapping(value = "")
    public ResponseEntity<IResponse> list(
                                        @RequestParam Optional<String> categoryName,
                                        @RequestParam Optional<Integer> page,
                                        @RequestParam Optional<Integer> size,
                                        @RequestParam Optional<String> sortBy,
                                        @RequestParam Optional<Boolean> sortDesc){
        String _sortBy = sortBy.orElse(null);

        Sort sort = null;
        if(!_sortBy.isEmpty()) {
            sort =   (sortDesc.orElse(false)) ? Sort.by(_sortBy).descending()
                    : Sort.by(_sortBy).ascending();
        }
        Pageable pageable = (sort!=null)? PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE),sort)
                : PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE));

        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),
                serviceCategoryService.getCategories(categoryName.orElse(""),pageable)), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Object> addCategory(@RequestBody ServiceCategory req){
        ServiceCategory serviceCategory = serviceCategoryService.addCategory(req);
        return new ResponseEntity<>(serviceCategory, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<IResponse> geteCategoryById(@PathVariable("id") Long id){
        Optional <ServiceCategory> serviceCategory = serviceCategoryService.findById(id);
        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),serviceCategory), HttpStatus.OK);
    }

    @GetMapping("/by-alias/{alias}")
    public ResponseEntity<IResponse> getServiceCategoryByAlias(@PathVariable("alias") String alias){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                serviceCategoryService.findByAlias(alias)
        ), HttpStatus.OK);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleException(HttpServletRequest req, DataIntegrityViolationException e) {
        return new ResponseEntity<>(new ExceptionResponse(Integer.parseInt(ResponseEnum.SERVICE_CATEGORY_NOT_UNIQUE.getCode()),
                "Sorry, please try later "),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
