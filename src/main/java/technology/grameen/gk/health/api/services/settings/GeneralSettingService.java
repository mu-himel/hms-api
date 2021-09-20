package technology.grameen.gk.health.api.services.settings;

import technology.grameen.gk.health.api.entity.GeneralSetting;

import java.util.Optional;

public interface GeneralSettingService {

    GeneralSetting save(GeneralSetting setting);

    Optional<GeneralSetting> getSetting();
}
