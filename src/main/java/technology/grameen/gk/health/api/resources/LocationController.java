package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.Village;
import technology.grameen.gk.health.api.requests.LocationMappingRequest;
import technology.grameen.gk.health.api.responses.*;
import technology.grameen.gk.health.api.services.LocationService;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/location")
public class LocationController {

    private static final Integer PAGE_SIZE = 25;
    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/divisions")
    public ResponseEntity<IResponse> divisions(){
       return new ResponseEntity<>(new EntityCollectionResponse<>(
               HttpStatus.OK.value(),
               locationService.getDivisionList()
       ),HttpStatus.OK);
    }

    @GetMapping("/villages")
    public ResponseEntity<IResponse> villages(
            @RequestParam Optional<Long> divisionId,
            @RequestParam Optional<Long> districtId,
            @RequestParam Optional<Long> thanaId,
            @RequestParam Optional<Long> unionId,
            @RequestParam Optional<String> villageCode,
            @RequestParam Optional<String> villageName,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<Boolean> sortDesc
    ){
        String _sortBy = sortBy.orElse(null);

        Sort sort = null;

        if(!_sortBy.isEmpty()) {
            sort =   (sortDesc.orElse(false)) ? Sort.by(_sortBy).descending()
                    : Sort.by(_sortBy).ascending();
        }
        Pageable pageable = (sort!=null)? PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE),sort)
                : PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE));


        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                locationService.getVillages(
                        divisionId.orElse(null),
                        districtId.orElse(null),
                        thanaId.orElse(null),
                        unionId.orElse(null),
                        villageCode.orElse(""),
                        villageName.orElse(""),
                        pageable)
        ),HttpStatus.OK);
    }

    @GetMapping("/districts/{divisionId}")
    public ResponseEntity<IResponse> districts(@PathVariable("divisionId") Long divisionId){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                locationService.getDistrictList(divisionId)
        ),HttpStatus.OK);
    }

    @GetMapping("/thanas/{districtId}")
    public ResponseEntity<IResponse> thanas(@PathVariable("districtId") Long districtId){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                locationService.getThanaList(districtId)
        ),HttpStatus.OK);
    }

    @GetMapping("/unions/{thanaId}")
    public ResponseEntity<IResponse> unions(@PathVariable("thanaId") Long thanaId){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                locationService.getUnionList(thanaId)
        ),HttpStatus.OK);
    }

    @GetMapping("/villages/{unionId}")
    public ResponseEntity<IResponse> villages(@PathVariable("unionId") Long unionId){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                locationService.getVillageList(unionId)
        ),HttpStatus.OK);
    }

    @GetMapping("/center-villages/{centerId}")
    public ResponseEntity<IResponse> villages1(@PathVariable("centerId") Long centerId){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                locationService.getCenterVillageList(centerId)
        ),HttpStatus.OK);
    }

    /**
     * Village and Center Mapping
     * @param req
     * @return
     */
    @PostMapping(value = "/mapping")
    public ResponseEntity<IResponse> mapping(@RequestBody LocationMappingRequest req){

        if(!locationService.villageMapping(req)){
            return new ResponseEntity<>(new ExceptionResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    "Mapping failed"),HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(new SimpleResponse(HttpStatus.OK.value(),
                "Successfully Mapped"),HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<IResponse> addLocation(@RequestBody Village req){
        if(req.getVillageName().isEmpty()){
            return new ResponseEntity<>(new ExceptionResponse(HttpStatus.OK.value(), "Name is Required")
                    , HttpStatus.OK);
        }
        Village village = locationService.addLocation(req);
        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(), village), HttpStatus.OK);
    }

    @GetMapping(value = "/village/{id}")
    public ResponseEntity<IResponse> getDetail(@PathVariable("id") Long villageId){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                locationService.findById(villageId)
        ), HttpStatus.OK);
    }




}
