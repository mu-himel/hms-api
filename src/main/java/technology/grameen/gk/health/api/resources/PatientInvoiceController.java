package technology.grameen.gk.health.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.GeneralSetting;
import technology.grameen.gk.health.api.entity.PatientInvoice;
import technology.grameen.gk.health.api.notification.sms.SmsService;
import technology.grameen.gk.health.api.projection.PatientInvoiceDetail;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.projection.PatientInvoiceAutoComplete;
import technology.grameen.gk.health.api.projection.PatientSearchResult;
import technology.grameen.gk.health.api.requests.InvoiceCreate;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.patient.PatientManageService;
import technology.grameen.gk.health.api.services.invoice.PatientInvoiceService;
import technology.grameen.gk.health.api.services.settings.GeneralSettingService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/invoice")
public class PatientInvoiceController {

    @Autowired
    private PatientInvoiceService patientInvoiceService;

    @Autowired
    private PatientManageService patientManageService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private GeneralSettingService generalSettingService;

    @GetMapping("/invoice-numbers/{invoiceNumber}")
    public ResponseEntity<IResponse> getByInvoiceNumber(@PathVariable("invoiceNumber") String invoiceNumber){
        List<PatientInvoiceAutoComplete> invoiceNumbers = patientInvoiceService.getInvoiceByNumber(invoiceNumber);
        return new ResponseEntity<>(new EntityCollectionResponse<>(HttpStatus.OK.value(),
                invoiceNumbers),HttpStatus.OK);
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<Optional<PatientInvoiceDetail>> getByInvoiceId(@PathVariable("id") Long invoiceId){
        return new ResponseEntity<>(patientInvoiceService.getInvoiceById(invoiceId),HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<IResponse> createInvoice(@RequestBody InvoiceCreate patient){
        try {
            Optional<PatientSearchResult> patient1 = null;
            PatientInvoice created = patientInvoiceService.createInvoice(patient);
            if(created.getId()!=null){

                patient1 = patientManageService.getPatientByPId(patient.getPid());
                if(patient1.isPresent()) {
                    String mobileNumber = patient1.get().getMobileNumber();
                    GeneralSetting generalSetting = generalSettingService.getSetting().get();
                    if (generalSetting != null && generalSetting.getEnableSmsNotification()) {
                        if (mobileNumber.length() == 11) {
                            String patientName = patient1.get().getFullName();
                            String pid = patient1.get().getPid();
                            smsService.sent(mobileNumber, patientName + "(" + pid + "), You have paid " + created.getPaidAmount() + " BDT. Your InvoiceID: " + created.getInvoiceNumber());
                        }
                    }
                }
            }
            return new ResponseEntity<>(new EntityResponse<PatientSearchResult>(HttpStatus.OK.value(),patient1.get()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/prescription-invoice-numbers/{centerId}/{employeeId}")
    public ResponseEntity<IResponse> getPrescriptionInvoice(@PathVariable("centerId") Integer centerId,
                                                            @PathVariable("employeeId") Long employeeId){



        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                patientInvoiceService.getPrescriptionInvoiceByNumber(centerId,employeeId)
        ),HttpStatus.OK);
    }

    @GetMapping("/lab-test-invoice-numbers/{centerId}")
    public ResponseEntity<IResponse> getLabTestInvoice(@PathVariable("centerId") Integer centerId){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                patientInvoiceService.getLabTestInvoiceByNumbers(centerId)
        ), HttpStatus.OK);
    }

    @GetMapping("/total-unposted-amount")
    public ResponseEntity<IResponse> getTotalUnPostedAmount(){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                patientInvoiceService.getTotalUnPostedAmount()
        ), HttpStatus.OK);
    }
}
