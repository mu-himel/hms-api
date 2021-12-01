package technology.grameen.gk.health.api.services.report.statistics;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.repositories.report.BusinessTargetStatRepository;
import technology.grameen.gk.health.api.services.HealthCenterService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessTargetStatServiceImpl implements BusinessTargetStatService{

    private BusinessTargetStatRepository businessTargetStatRepository;
    private HealthCenterService healthCenterService;

    public BusinessTargetStatServiceImpl(BusinessTargetStatRepository businessTargetStatRepository,
                                         HealthCenterService healthCenterService) {
        this.businessTargetStatRepository = businessTargetStatRepository;
        this.healthCenterService = healthCenterService;
    }

    @Override
    public List<?> getStats(Long regionId, Short officeTypeId, String yearMonth, String fromDate) {
        if(officeTypeId == null){
            List<Map<String,?>> results = new ArrayList<>();
            List<HealthCenter> healthCenters = healthCenterService.getCentersByOfficeTypeId(5);
            healthCenters.forEach(hc->{
                Map<String, Object> map = new HashMap<>();
                map.put("name",hc.getName());
                map.put("id",hc.getId());
                map.put("stats",
                businessTargetStatRepository.getRegionGroupWiseStats(hc.getId(), yearMonth, fromDate)
                );
                results.add(map);
            });
            return results;
        }

        if(officeTypeId==6) {
            return businessTargetStatRepository.getCenterStats(regionId, yearMonth, fromDate);
        }else{
            return businessTargetStatRepository.getRegionStats(regionId,yearMonth,fromDate);
        }
    }
}
