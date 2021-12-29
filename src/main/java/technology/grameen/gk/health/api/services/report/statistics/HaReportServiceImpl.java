package technology.grameen.gk.health.api.services.report.statistics;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.repositories.report.HaReportRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class HaReportServiceImpl implements HaReportService{

    public static final String REGULAR = "regular";
    public static final String CAMP    = "camp";
    public static final String SATELLITE = "satellite";

    private HaReportRepository haReportRepository;

    public HaReportServiceImpl(HaReportRepository haReportRepository) {
        this.haReportRepository = haReportRepository;
    }

    @Override
    public Map<?, ?> getChaReportByMonth(String month) {
        Map<String,Object> result = new HashMap<>();
        result.put("patientReferToHc", haReportRepository.getPatientReferByMonth(month,REGULAR));
        result.put("patientReferToCamp", haReportRepository.getPatientReferByMonth(month,CAMP));
        result.put("patientReferToSatellite", haReportRepository.getPatientReferByMonth(month,SATELLITE));
        result.put("cardRegCount", haReportRepository.getServiceCountByMonth(month,"card registration"));
        result.put("usg", haReportRepository.getServiceCountByMonth(month,"sonograph"));
        result.put("xRay", haReportRepository.getServiceCountByMonth(month,"x-ray"));
        result.put("cataract", haReportRepository.getServiceCountByMonth(month,"cataract"));
        result.put("delivery", haReportRepository.getServiceCountByMonth(month,"delivery"));
        result.put("diabetic", haReportRepository.getServiceCountByMonth(month,"diabetic"));
        result.put("vaccine",haReportRepository.getVaccineCountByMonth(month));
        result.put("patientVisit",haReportRepository.getPatientCheckupByMonth(month));
        result.put("homeVisit",haReportRepository.getHomeVisitByMonth(month));
        return result;
    }
}
