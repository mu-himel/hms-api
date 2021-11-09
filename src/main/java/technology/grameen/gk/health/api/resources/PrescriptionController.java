package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.entity.PatientDiseaseProfile;
import technology.grameen.gk.health.api.entity.PatientInvoice;
import technology.grameen.gk.health.api.entity.Prescription;
import technology.grameen.gk.health.api.requests.PrescriptionCreateRequest;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.patient.PatientDiseaseProfileService;
import technology.grameen.gk.health.api.services.prescription.PrescriptionService;

import java.util.HashSet;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/prescription")
public class PrescriptionController {

    private final Integer PAGE_SIZE = 10;

    private PrescriptionService prescriptionService;
    private PatientDiseaseProfileService patientDiseaseProfileService;

    public PrescriptionController(PrescriptionService prescriptionService,
                                  PatientDiseaseProfileService patientDiseaseProfileService) {
        this.prescriptionService = prescriptionService;
        this.patientDiseaseProfileService = patientDiseaseProfileService;
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> addPrescription(@RequestBody PrescriptionCreateRequest createRequest){
        Prescription prescription = createRequest.getPrescription();
        PatientDiseaseProfile patientDiseaseProfile = createRequest.getPatientDiseaseProfile();
        Prescription newPrescription = prescriptionService.savePrescription(createRequest.getPrescription());

        if(patientDiseaseProfile!=null){
            patientDiseaseProfileService.addDiseaseProfile(patientDiseaseProfile);
        }
        if(prescription.getId()>0){
            return new ResponseEntity<>(new EntityResponse<>(
                    HttpStatus.OK.value(),
                    prescriptionService.getPrescriptionById(prescription.getId())),HttpStatus.OK);
        }
        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),newPrescription), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<IResponse> getPrescriptions(
                                                      @RequestParam Optional<String> pNumber,
                                                      @RequestParam Optional<String> fullName,
                                                      @RequestParam Optional<String> date,
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
                prescriptionService.getPrescriptions(pNumber.orElse(""),
                                                fullName.orElse(""),
                        date.orElse(""),pageable)),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IResponse> getPrescriptionById(@PathVariable("id") Long id){
        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),
                prescriptionService.getPrescriptionById(id)),HttpStatus.OK);
    }

    @GetMapping("/{patientId}/{invoiceId}")
    public ResponseEntity<IResponse> getPrescriptionById(@PathVariable("patientId") Long patientId,
                                                         @PathVariable("invoiceId") Long invoiceId){
        Patient patient = new Patient();
        PatientInvoice patientInvoice = new PatientInvoice();
        patient.setId(patientId);
        patientInvoice.setId(invoiceId);
        return new ResponseEntity<>(new EntityResponse<>(HttpStatus.OK.value(),

                prescriptionService.getPrescriptionByPatientAndInvoice(patient,patientInvoice)),HttpStatus.OK);
    }
}
