package technology.grameen.gk.health.api.notification.sms.adn;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import technology.grameen.gk.health.api.notification.sms.SmsService;
import technology.grameen.gk.health.api.notification.sms.adn.request.CheckBalanceRequest;
import technology.grameen.gk.health.api.notification.sms.adn.request.SingleSmsRequest;
import technology.grameen.gk.health.api.notification.sms.adn.response.CheckBalanceResponse;
import technology.grameen.gk.health.api.notification.sms.adn.response.SingleSmsResponse;
import technology.grameen.gk.health.api.notification.sms.entity.SmsSentResponse;
import technology.grameen.gk.health.api.notification.sms.service.SmsSentResponseService;

@Service
public class AdnSmsServiceImpl implements SmsService {

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private Environment env;
    private SmsSentResponseService smsSentResponseService;
    public AdnSmsServiceImpl(Environment env, SmsSentResponseService smsSentResponseService) {
        this.restTemplate = new RestTemplate();
        this.httpHeaders = new HttpHeaders();
        this.env = env;
        this.smsSentResponseService = smsSentResponseService;
    }

    @Override
    public SingleSmsResponse sent(String to, String body) {

        SingleSmsRequest singleSmsRequest = new SingleSmsRequest();
        singleSmsRequest.setApi_key(env.getProperty("api-key"));
        singleSmsRequest.setApi_secret(env.getProperty("api-secret"));
        singleSmsRequest.setMobile(to);
        singleSmsRequest.setMessage_body(body);

        String url = env.getProperty("sms-url")+"/send-sms";
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        SingleSmsResponse response = null;

        response = restTemplate.postForObject(url,singleSmsRequest, SingleSmsResponse.class);
        if(response.getApi_response_code()==200){

            SmsSentResponse smsSentResponse = new SmsSentResponse();
            smsSentResponse.setSmsUid(response.getSms_uid());
            smsSentResponse.setMobileNumber(singleSmsRequest.getMobile());
            smsSentResponse.setRequestType(singleSmsRequest.getRequest_type());
            smsSentResponse.setResponseCode(response.getApi_response_code());
            smsSentResponse.setResponseMsg(response.getApi_response_message());

            smsSentResponseService.save(smsSentResponse);
        }
        return response;
    }

    @Override
    public <T> CheckBalanceResponse checkBalance(T t) {
        CheckBalanceRequest checkBalanceRequest = ((CheckBalanceRequest) t);
        String url = env.getProperty("sms-url")+"/check-balance";

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        CheckBalanceResponse response = null;

        response = restTemplate.postForObject(url,checkBalanceRequest,CheckBalanceResponse.class);
        return response;
    }
}
