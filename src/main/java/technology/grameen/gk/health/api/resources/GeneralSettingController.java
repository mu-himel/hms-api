package technology.grameen.gk.health.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.GeneralSetting;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.settings.GeneralSettingService;

@RestController
@RequestMapping("/api/v1/general-setting")
public class GeneralSettingController {

    private GeneralSettingService generalSettingService;

    public GeneralSettingController(GeneralSettingService generalSettingService) {
        this.generalSettingService = generalSettingService;
    }

    @GetMapping("")
    public ResponseEntity<IResponse> getGeneralSetting(){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                generalSettingService.getSetting()
        ), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<IResponse> addGeneralSetting(@RequestBody GeneralSetting generalSetting){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                generalSettingService.save(generalSetting)
        ), HttpStatus.OK);
    }
}
