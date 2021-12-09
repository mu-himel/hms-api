package technology.grameen.gk.health.api.services.business_target;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.BusinessTarget;

public interface BusinessTargetService {

    BusinessTarget addBusinessTarget(BusinessTarget businessTarget);

    Page<?> getAll(Pageable pageable);
}
