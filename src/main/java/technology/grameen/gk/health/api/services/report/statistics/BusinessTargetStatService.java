package technology.grameen.gk.health.api.services.report.statistics;

import technology.grameen.gk.health.api.exceptions.CustomException;

import java.util.List;

public interface BusinessTargetStatService {

   List<?> getStats(Long regionId, Short officeTypeId, String yearMonth, String fromDate) throws CustomException;
}
