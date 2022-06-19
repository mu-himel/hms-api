package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.MedicineType;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.services.medicine.MedicineTypeService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/medicine-types")
public class MedicineTypeController {

    private static final Integer PAGE_SIZE = 20;
    MedicineTypeService medicineTypeService;

    MedicineTypeController(MedicineTypeService medicineTypeService){
        this.medicineTypeService = medicineTypeService;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody MedicineType medicineType){
        medicineTypeService.addMedicineType(medicineType);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getMedicineTypeByName(@RequestParam String name){
        return new ResponseEntity<>(
                medicineTypeService.getByName(name),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<?> getMedicines(@RequestParam Optional<Integer> page,
                                          @RequestParam Optional<Integer> size){

        Pageable pageable = PageRequest.of(page.orElse(0),size.orElse(PAGE_SIZE));
        return new ResponseEntity<>(
                medicineTypeService.getPage(pageable),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody MedicineType medicineType)
            throws CustomException {
        medicineTypeService.addMedicineType(id,medicineType);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
