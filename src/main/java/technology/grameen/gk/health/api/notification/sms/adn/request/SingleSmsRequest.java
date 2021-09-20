package technology.grameen.gk.health.api.notification.sms.adn.request;

import technology.grameen.gk.health.api.notification.sms.SmsRequest;

public class SingleSmsRequest implements SmsRequest {
    private String request_type = "SINGLE_SMS";
    private String api_key;
    private String api_secret;
    private String message_type = "TEXT";
    private String mobile;
    private String message_body;

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getApi_secret() {
        return api_secret;
    }

    public void setApi_secret(String api_secret) {
        this.api_secret = api_secret;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public String getRequest_type() {
        return request_type;
    }

    public String getMessage_type() {
        return message_type;
    }
}
