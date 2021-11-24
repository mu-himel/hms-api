package technology.grameen.gk.health.api.services.report.statistics;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.repositories.report.BusinessTargetStatRepository;

import java.util.List;

@Service
public class BusinessTargetStatServiceImpl implements BusinessTargetStatService{

    private BusinessTargetStatRepository businessTargetStatRepository;

    public BusinessTargetStatServiceImpl(BusinessTargetStatRepository businessTargetStatRepository) {
        this.businessTargetStatRepository = businessTargetStatRepository;
    }

    @Override
    public List<?> getStats(Long regionId, Short officeTypeId, String yearMonth, String fromDate) throws CustomException {
        if(officeTypeId == null){
            throw new CustomException("Sorry! Office Type Missing");
        }

        if(officeTypeId==6) {
            return businessTargetStatRepository.getCenterStats(regionId, yearMonth, fromDate);
        }else{
            return businessTargetStatRepository.getRegionStats(regionId,yearMonth,fromDate);
        }
    }
}
