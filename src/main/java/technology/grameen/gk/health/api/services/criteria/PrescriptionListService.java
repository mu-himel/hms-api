package technology.grameen.gk.health.api.services.criteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.projection.PrescriptionListItem;
import technology.grameen.gk.health.api.responses.PrescriptionList;

public interface PrescriptionListService {

    Page<PrescriptionList> getPrescriptions(String regionCode, String centerCode, String pNumber, String fullName, String date, Pageable pageable);
}
