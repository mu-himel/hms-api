package technology.grameen.gk.health.api.notification.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.notification.sms.entity.SmsSentResponse;
import technology.grameen.gk.health.api.notification.sms.repository.SmsSentResponseRepository;

@Service
public class SmsSentResponseServiceImpl implements SmsSentResponseService{

    private SmsSentResponseRepository repository;

    public SmsSentResponseServiceImpl(SmsSentResponseRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public SmsSentResponse save(SmsSentResponse ssr) {
        return repository.save(ssr);
    }
}
