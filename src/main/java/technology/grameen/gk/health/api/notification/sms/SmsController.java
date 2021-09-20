package technology.grameen.gk.health.api.notification.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technology.grameen.gk.health.api.notification.sms.adn.request.CheckBalanceRequest;
import technology.grameen.gk.health.api.notification.sms.adn.request.SingleSmsRequest;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;

@RestController
@RequestMapping("/api/v1/sms")
public class SmsController {

    @Autowired
    private Environment env;

    @Autowired
    private SmsService smsService;

    @GetMapping("/check-balance")
    public ResponseEntity<IResponse> checkBalance(){
        CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
        checkBalanceRequest.setApi_key(env.getProperty("api-key"));
        checkBalanceRequest.setApi_secret(env.getProperty("api-secret"));
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                smsService.checkBalance(checkBalanceRequest)
        ), HttpStatus.OK);
    }

    @GetMapping("/sent")
    public ResponseEntity<IResponse> sendSms(){

        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                smsService.sent("01714112912","Test Message")
        ), HttpStatus.OK);
    }
}
