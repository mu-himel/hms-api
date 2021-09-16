package technology.grameen.gk.health.api.network;


import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import technology.grameen.gk.health.api.entity.Voucher;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.requests.VoucherSendRequest;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.VoucherSendResponse;
import technology.grameen.gk.health.api.services.invoice.PatientInvoiceService;
import technology.grameen.gk.health.api.services.voucher.VoucherService;

import java.time.LocalDateTime;

@Service
@PropertySource(value = "classpath:application-dev.properties")
public class AccountServiceImpl implements AccountService{

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private HttpHeaders httpHeaders;
    private RestTemplate restTemplate;

    @Autowired
    private KeycloakRestTemplate template;

    private Environment env;
    private VoucherService voucherService;
    private PatientInvoiceService patientInvoiceService;

    public AccountServiceImpl(Environment env,
                              VoucherService voucherService) {
        this.httpHeaders = new HttpHeaders();
        this.restTemplate = new RestTemplate();
        this.env = env;
        this.voucherService = voucherService;
    }

    @Override
    @Transactional
    public Object sendVoucher(VoucherSendRequest req) throws Exception {
        String accountsUrl = env.getProperty("accounts")+env.getProperty("auto-voucher");

        Voucher voucher = voucherService.addVoucher(getVoucher(req));
        if(voucher.getId()>0 && patientInvoiceService != null){
            patientInvoiceService.postInvoice();
        }

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization","Bearer "+req.getToken());

        ResponseEntity<VoucherSendResponse> response = null;
        HttpEntity<VoucherSendRequest> requestBody = new HttpEntity<>(req,httpHeaders);
        VoucherSendResponse body = null;
        try {
            response = template.exchange(accountsUrl, HttpMethod.POST, requestBody, VoucherSendResponse.class);
            body = response.getBody();

            updateVoucher(voucher,body);

        }catch (HttpClientErrorException ex){

            voucher.setStatus("failed");
            voucherService.updateVoucher(voucher);

            String msg = ex.getMessage();
            if(ex.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY){
                msg = msg.substring(51,msg.charAt('.')-1);
            }
            throw new CustomException(msg);
        }
        return voucher;
    }

    private Voucher updateVoucher(Voucher voucher, VoucherSendResponse body){
        voucher.setCreditAmount(body.getObject().getTotalCreditAmount());
        voucher.setDebitAmount(body.getObject().getTotalDebitAmount());
        voucher.setDebitHead(body.getDebitAcc().getTitle());
        voucher.setDebitHeadId(body.getDebitAcc().getId());
        voucher.setCreditHead(body.getCreditAcc().getTitle());
        voucher.setCreditHeadId(body.getCreditAcc().getId());
        voucher.setVoucherType(body.getObject().getVoucherType().getName());
        voucher.setStatus("completed");
        voucherService.updateVoucher(voucher);
        return voucher;
    }

    private Voucher getVoucher(VoucherSendRequest req){
        Voucher voucher = new Voucher();
        voucher.setAlias(req.getAlias());
        voucher.setOfficeId(req.getOfficeId());
        voucher.setVoucherNo(req.getVoucherNo());
        voucher.setNote(req.getNote());
        voucher.setVoucherDate(req.getVoucherDate());
        voucher.setModule(req.getModule());
        voucher.setStatus("pending");
        voucher.setCreatedAt(LocalDateTime.now());
        return voucher;
    }

    @Override
    public EntityCollectionResponse getAlias(String moduleName, String token) {

        String _token = token.replace("Bearer","").trim();
        logger.info("TOKEN :"+_token);
        logger.info("MODULE :"+moduleName);
        String accountsUrl = env.getProperty("accounts")+env.getProperty("get-auto-voucher-alias")+moduleName;
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.add("Authorization","Bearer "+_token);
//        HttpEntity<MultiValueMap<String, Object>> requestBody = new HttpEntity<>(httpHeaders);
        ResponseEntity<EntityCollectionResponse> response = null;
        EntityCollectionResponse body = null;
        try {
            response = template.getForEntity(accountsUrl,EntityCollectionResponse.class);
            body = response.getBody();
            logger.info(body.toString());


        }catch (HttpClientErrorException ex){

            ex.printStackTrace();
        }
        return body;
    }

    @Override
    public void setPatientInvoiceService(PatientInvoiceService patientInvoiceService) {
        this.patientInvoiceService = patientInvoiceService;
    }
}
