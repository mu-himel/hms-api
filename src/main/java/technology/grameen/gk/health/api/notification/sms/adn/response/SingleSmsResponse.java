package technology.grameen.gk.health.api.notification.sms.adn.response;

import technology.grameen.gk.health.api.notification.sms.SmsResponse;

import java.util.List;

public class SingleSmsResponse implements SmsResponse {

    private String request_type;
    private String campaign_uid;
    private String sms_uid;
    private List<String> invalid_numbers;
    private Long api_response_code;
    private String api_response_message;

    public SingleSmsResponse(){}

    public SingleSmsResponse(String request_type, String campaign_uid, String sms_uid,
                             List<String> invalid_numbers, Long api_response_code,
                             String api_response_message) {
        this.request_type = request_type;
        this.campaign_uid = campaign_uid;
        this.sms_uid = sms_uid;
        this.invalid_numbers = invalid_numbers;
        this.api_response_code = api_response_code;
        this.api_response_message = api_response_message;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getCampaign_uid() {
        return campaign_uid;
    }

    public void setCampaign_uid(String campaign_uid) {
        this.campaign_uid = campaign_uid;
    }

    public String getSms_uid() {
        return sms_uid;
    }

    public void setSms_uid(String sms_uid) {
        this.sms_uid = sms_uid;
    }

    public List<String> getInvalid_numbers() {
        return invalid_numbers;
    }

    public void setInvalid_numbers(List<String> invalid_numbers) {
        this.invalid_numbers = invalid_numbers;
    }

    public Long getApi_response_code() {
        return api_response_code;
    }

    public void setApi_response_code(Long api_response_code) {
        this.api_response_code = api_response_code;
    }

    public String getApi_response_message() {
        return api_response_message;
    }

    public void setApi_response_message(String api_response_message) {
        this.api_response_message = api_response_message;
    }
}
