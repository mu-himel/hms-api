package technology.grameen.gk.health.api.notification.sms.adn.response;

import technology.grameen.gk.health.api.notification.sms.SmsResponse;

public class CheckBalanceResponse implements SmsResponse {

    public CheckBalanceResponse() {}

    public CheckBalanceResponse(Balance balance, Integer api_response_code, String api_response_message) {
        this.balance = balance;
        this.api_response_code = api_response_code;
        this.api_response_message = api_response_message;
    }

    private Balance balance;
    private Integer api_response_code;
    private String api_response_message;

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public Integer getApi_response_code() {
        return api_response_code;
    }

    public void setApi_response_code(Integer api_response_code) {
        this.api_response_code = api_response_code;
    }

    public String getApi_response_message() {
        return api_response_message;
    }

    public void setApi_response_message(String api_response_message) {
        this.api_response_message = api_response_message;
    }
}
