package technology.grameen.gk.health.api.notification.sms;

import technology.grameen.gk.health.api.notification.sms.adn.response.SingleSmsResponse;

import java.io.Serializable;

public interface SmsService extends Serializable {

    <T> SmsResponse checkBalance(T t);
    SmsResponse sent(String to, String body);
}
