package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.LabTest;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.entity.PatientInvoice;
import technology.grameen.gk.health.api.entity.Service;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.responses.SimpleResponse;
import technology.grameen.gk.health.api.services.labtest.LabTestService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/lab-test")
public class LabReportController {

    private final Integer PAGE_SIZE = 5;

    LabTestService labTestService;

    LabReportController(LabTestService labTestService){
        this.labTestService = labTestService;
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> createLabReport(@RequestBody LabTest labTest){
        LabTest labTest1 = labTestService.saveLabTest(labTest);
        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),
                labTest1), HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<IResponse> updateLabReport(@RequestBody LabTest labTest){
        labTestService.saveLabTest(labTest);
        return new ResponseEntity<>(new SimpleResponse(HttpStatus.OK.value(),
                "Successfully updated"), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<IResponse> getLabTestReports(@RequestParam Optional<String>  invoiceNumber,
                                                       @RequestParam Optional<String> fullName,
                                                       @RequestParam Optional<String> pid,
                                                       @RequestParam Optional<String> status,
                                                       @RequestParam Optional<Long> officeId,
                                                       @RequestParam Optional<Short> typeId,
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

        return  new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),
                labTestService.getLabTestReports(invoiceNumber.orElse(""),
                        fullName.orElse(""),
                        pid.orElse("") ,
                        status.orElse(""),
                        officeId.orElse(null),
                        typeId.orElse(null),pageable)),HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<IResponse> getLabTestReportById(@PathVariable("id") Long id){

        return  new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),
                labTestService.getLabTestReportById(id)),HttpStatus.OK);

    }

    @GetMapping("/{patientId}/{invoiceId}/{serviceId}")
    public ResponseEntity<IResponse> getLabTestReportById(@PathVariable("patientId") Long patientId,
                                                          @PathVariable("invoiceId") Long invoiceId,
                                                          @PathVariable("serviceId") Long serviceId){
        Patient patient = new Patient();
        PatientInvoice patientInvoice = new PatientInvoice();
        Service service = new Service();

        patient.setId(patientId);
        patientInvoice.setId(invoiceId);
        service.setServiceId(serviceId);

        return  new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),
                labTestService.getLabTestReportByPatientInvoiceService(patient, patientInvoice, service)),HttpStatus.OK);

    }

}
