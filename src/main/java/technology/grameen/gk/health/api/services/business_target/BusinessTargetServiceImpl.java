package technology.grameen.gk.health.api.services.business_target;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.BusinessTarget;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.repositories.BusinessTargetRepository;
import technology.grameen.gk.health.api.requests.BusinessTargetRequest;

import java.util.List;

@Service
public class BusinessTargetServiceImpl implements BusinessTargetService{

    private BusinessTargetRepository businessTargetRepository;

    public BusinessTargetServiceImpl(BusinessTargetRepository businessTargetRepository) {
        this.businessTargetRepository = businessTargetRepository;
    }

    @Override
    @Transactional
    public Boolean addBusinessTarget(BusinessTargetRequest businessTargetRequest) throws CustomException {
        List<BusinessTarget> businessTargetList = businessTargetRequest.getBusinessTargets();
        if(businessTargetList.size()==0){
            throw new CustomException("Sorry! cannot save blank data");
        }
        businessTargetRepository.saveAll(businessTargetList);
        BusinessTarget businessTarget = businessTargetList.get(0);

        return true;
    }

    @Override
    public Page<?> getAll(Pageable pageable) {
        return businessTargetRepository.findAllBusinessTarget(pageable);
    }

    @Override
    public List<?> getAllByYearMonthAndCreatedOffice(String yearMonth, Long createdOfficeId) {
        return businessTargetRepository.findByCreatedOfficeAndYearMonth(createdOfficeId,yearMonth);
    }
}
