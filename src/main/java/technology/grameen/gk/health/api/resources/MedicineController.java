package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.Medicine;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.ExceptionResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.medicine.MedicineService;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/medicine")
public class MedicineController {

    private final Integer PAGE_SIZE = 10;

    private MedicineService medicineService;

    public MedicineController (MedicineService medicineService){
        this.medicineService = medicineService;
    }

    @GetMapping(value="")
    public ResponseEntity<IResponse> list(
                                          @RequestParam Optional<String> medicineName,
                                          @RequestParam Optional<Integer> page,
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

        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),
                medicineService.getMedicines(medicineName.orElse(""),pageable) ), HttpStatus.OK);
    }

//    @GetMapping("/list")
//    public ResponseEntity<IResponse> getMedicineList(){
//        return new ResponseEntity<>(new EntityCollectionResponse<>(
//                HttpStatus.OK.value(),
//                medicineService.getMedicines()
//        ),HttpStatus.OK);
//    }

    @GetMapping("/list")
    public ResponseEntity<IResponse> getMedicineListByName(@RequestParam Optional<String> medicineName){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                medicineService.getMedicines(medicineName.orElse(""))
        ),HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<IResponse> addMedicine(@RequestBody Medicine req) throws CustomException {
        if(req.getName().isEmpty()){
            return new ResponseEntity<>(new ExceptionResponse(HttpStatus.OK.value(), "Name is Required")
                    , HttpStatus.OK);
        }
        Medicine medicine = medicineService.addMedicine(req);
        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(), medicine), HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<IResponse> getMedicineById(@PathVariable("id") Long id){
      Optional <Medicine> medicine = medicineService.findById(id);
        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(), medicine), HttpStatus.OK);
    }



}
