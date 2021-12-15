package technology.grameen.gk.health.api.services.business_target;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.BusinessTarget;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.requests.BusinessTargetRequest;

import java.util.List;

public interface BusinessTargetService {

    Boolean addBusinessTarget(BusinessTargetRequest businessTargetRequest) throws CustomException;

    Page<?> getAll(Pageable pageable);

    List<?> getAllByYearMonthAndCreatedOffice(String yearMonth, Long createdOfficeId);
}
