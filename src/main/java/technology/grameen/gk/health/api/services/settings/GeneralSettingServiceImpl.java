package technology.grameen.gk.health.api.services.settings;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.GeneralSetting;
import technology.grameen.gk.health.api.repositories.GeneralSettingRepository;

import java.util.Optional;

@Service
public class GeneralSettingServiceImpl implements GeneralSettingService{

    private GeneralSettingRepository repository;

    public GeneralSettingServiceImpl(GeneralSettingRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public GeneralSetting save(GeneralSetting setting) {
        return repository.save(setting);
    }

    @Override
    public Optional<GeneralSetting> getSetting() {
        return repository.findAll().stream().findFirst();
    }
}
