package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.network.AccountService;
import technology.grameen.gk.health.api.requests.VoucherSendRequest;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.ExceptionResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.invoice.PatientInvoiceService;
import technology.grameen.gk.health.api.services.voucher.VoucherService;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/accounts/voucher")
public class AccountController {

    private static final Integer PAGE_SIZE = 10;
    private AccountService accountService;
    private VoucherService voucherService;
    private PatientInvoiceService patientInvoiceService;


    public AccountController(AccountService accountService, VoucherService voucherService, PatientInvoiceService patientInvoiceService) {
        this.accountService = accountService;
        this.voucherService = voucherService;
        this.patientInvoiceService = patientInvoiceService;
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> sendVoucher(@RequestHeader("Authorization") String token,
                                                 @RequestBody VoucherSendRequest voucherSendRequest) throws Exception {

        String _token = token.replace("Bearer","").trim();

        if(voucherSendRequest.getAlias().equalsIgnoreCase("patient-service")) {
            BigDecimal totalAmount = (patientInvoiceService.getTotalUnPostedAmount().isPresent()) ?
                    patientInvoiceService.getTotalUnPostedAmount().get() : BigDecimal.valueOf(0);
            voucherSendRequest.setTotalAmount(totalAmount);
            accountService.setPatientInvoiceService(patientInvoiceService);
        }
        voucherSendRequest.setAlias(voucherSendRequest.getAlias());
        voucherSendRequest.setToken(_token);

        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                accountService.sendVoucher(voucherSendRequest)
        ),HttpStatus.OK);
    }

    @GetMapping("/list")
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

            String _sortBy = sortBy.orElse("voucherDate");
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
                voucherService.getVouchers(pageable)
        ), HttpStatus.OK);

    }

    @GetMapping("/by-module/{moduleName}")
    public ResponseEntity<IResponse> getAlias(@PathVariable("moduleName") String moduleName,
                                              @RequestHeader("Authorization") String token){
        EntityCollectionResponse alias =  accountService.getAlias(moduleName,token);
        return new ResponseEntity<>(alias,HttpStatus.OK);
    }
}
