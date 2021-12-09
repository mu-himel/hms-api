package technology.grameen.gk.health.api.services.business_target;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.BusinessTarget;
import technology.grameen.gk.health.api.repositories.BusinessTargetRepository;

@Service
public class BusinessTargetServiceImpl implements BusinessTargetService{

    private BusinessTargetRepository businessTargetRepository;

    public BusinessTargetServiceImpl(BusinessTargetRepository businessTargetRepository) {
        this.businessTargetRepository = businessTargetRepository;
    }

    @Override
    @Transactional
    public BusinessTarget addBusinessTarget(BusinessTarget businessTarget) {

        return businessTargetRepository.save(businessTarget);
    }

    @Override
    public Page<?> getAll(Pageable pageable) {
        return businessTargetRepository.findAllBusinessTarget(pageable);
    }
}
