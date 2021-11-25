package technology.grameen.gk.health.api.services.report.statistics;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.repositories.report.PatientRegStatsRepository;
import technology.grameen.gk.health.api.repositories.report.PatientVisitStatsRepository;
import technology.grameen.gk.health.api.repositories.report.ServiceSaleReportRepository;
import technology.grameen.gk.health.api.services.HealthCenterService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardStatisticServiceImpl implements DashboardStatisticService{

    PatientRegStatsRepository reportRepository;
    ServiceSaleReportRepository ssReportRepository;
    PatientVisitStatsRepository pvsReportRepository;
    HealthCenterService healthCenterService;

    public DashboardStatisticServiceImpl(PatientRegStatsRepository reportRepository,
                                         ServiceSaleReportRepository ssReportRepository,
                                         HealthCenterService healthCenterService,
                                         PatientVisitStatsRepository pvsReportRepository) {
        this.reportRepository = reportRepository;
        this.ssReportRepository = ssReportRepository;
        this.healthCenterService = healthCenterService;
        this.pvsReportRepository = pvsReportRepository;
    }

    private List<Long> getCenters(String regionCode, String centerCode){
        List<Long> centers = new ArrayList<>();
        if(regionCode != "" && centerCode==""){
            centers = healthCenterService.getCenterIdByThirdLevel(regionCode);

        }else if(regionCode =="" && centerCode!=""){
            HealthCenter center = healthCenterService.getCenterByCenterCode(centerCode).get();
            if(center!=null){
                centers.add(center.getId());
            }
        }else if(regionCode !="" && centerCode!=""){
            HealthCenter center = healthCenterService.getCenterByCenterCode(centerCode).get();
            if(center!=null){
                centers.add(center.getId());
            }

        }else{
            centers = new ArrayList<>();
        }

        return centers;
    }

    @Override
    public Map<String, Object> getPatientVisitStats(String regionCode, String centerCode,String type,
                                               String fromDate, String toDate) {
        List<Long> centers = getCenters(regionCode,centerCode);
        Map<String,Object> map = new HashMap<>();

        if(type.equalsIgnoreCase("daily")){
            if(centers.size()==0){
                map.put("chGbReg",pvsReportRepository.getPatientCHCount(true, fromDate));
                map.put("chNGbReg",pvsReportRepository.getPatientCHCount(false, fromDate));
                map.put("nchGbReg",pvsReportRepository.getPatientNCHCount(true, fromDate));
                map.put("nchNGbReg",pvsReportRepository.getPatientNCHCount( false, fromDate));
            }else{
                fromDate = fromDate.substring(0,10);
                map.put("chGbReg",pvsReportRepository.getPatientCHCount(centers, true, fromDate));
                map.put("chNGbReg",pvsReportRepository.getPatientCHCount(centers, false, fromDate));
                map.put("nchGbReg",pvsReportRepository.getPatientNCHCount(centers, true, fromDate));
                map.put("nchNGbReg",pvsReportRepository.getPatientNCHCount(centers, false, fromDate));
            }

        }else if(type.equalsIgnoreCase("monthly")){
            if(centers.size()==0) {
                map.put("chGbReg", pvsReportRepository.getMonthlyPatientCHCount(true, fromDate));
                map.put("chNGbReg", pvsReportRepository.getMonthlyPatientCHCount(false, fromDate));
                map.put("nchGbReg", pvsReportRepository.getMonthlyPatientNCHCount(true, fromDate));
                map.put("nchNGbReg", pvsReportRepository.getMonthlyPatientNCHCount(false, fromDate));
            }else{
                map.put("chGbReg", pvsReportRepository.getMonthlyPatientCHCount(centers, true, fromDate));
                map.put("chNGbReg", pvsReportRepository.getMonthlyPatientCHCount(centers, false, fromDate));
                map.put("nchGbReg", pvsReportRepository.getMonthlyPatientNCHCount(centers, true, fromDate));
                map.put("nchNGbReg", pvsReportRepository.getMonthlyPatientNCHCount(centers, false, fromDate));
            }
        }else if(type.equalsIgnoreCase("range")){
            LocalDateTime fromDateLDT = LocalDateTime.parse(fromDate);
            LocalDateTime toDateLDT = LocalDateTime.parse(toDate);
            if(centers.size()==0) {
                map.put("chGbReg", pvsReportRepository.getRangePatientCHCount(true, fromDateLDT, toDateLDT));
                map.put("chNGbReg", pvsReportRepository.getRangePatientCHCount(false, fromDateLDT, toDateLDT));
                map.put("nchGbReg", pvsReportRepository.getRangePatientNCHCount(true, fromDateLDT, toDateLDT));
                map.put("nchNGbReg", pvsReportRepository.getRangePatientNCHCount(false, fromDateLDT, toDateLDT));
            }else {
                map.put("chGbReg", pvsReportRepository.getRangePatientCHCount(centers, true, fromDateLDT, toDateLDT));
                map.put("chNGbReg", pvsReportRepository.getRangePatientCHCount(centers, false, fromDateLDT, toDateLDT));
                map.put("nchGbReg", pvsReportRepository.getRangePatientNCHCount(centers, true, fromDateLDT, toDateLDT));
                map.put("nchNGbReg", pvsReportRepository.getRangePatientNCHCount(centers, false, fromDateLDT, toDateLDT));
            }
        }
        return map;
    }



    @Override
    public Map<String, Object> getPatientRegistrationStats(String regionCode, String centerCode,String type,
                                                           String fromDate, String toDate) {
        List<Long> centers = getCenters(regionCode,centerCode);

        Map<String,Object> map = new HashMap<>();
        if(type.equalsIgnoreCase("daily")){
            if(centers.size()==0){

                map.put("chGbReg",reportRepository.getPatientCHCount(true, fromDate));
                map.put("chNGbReg",reportRepository.getPatientCHCount(false, fromDate));
                map.put("nchGbReg",reportRepository.getPatientNCHCount(true, fromDate));
                map.put("nchNGbReg",reportRepository.getPatientNCHCount( false, fromDate));
            }else{
                fromDate = fromDate.substring(0,10);
                map.put("chGbReg",reportRepository.getPatientCHCount(centers, true, fromDate));
                map.put("chNGbReg",reportRepository.getPatientCHCount(centers, false, fromDate));
                map.put("nchGbReg",reportRepository.getPatientNCHCount(centers, true, fromDate));
                map.put("nchNGbReg",reportRepository.getPatientNCHCount(centers, false, fromDate));
            }

        }else if(type.equalsIgnoreCase("monthly")){
            if(centers.size()==0) {
                map.put("chGbReg", reportRepository.getMonthlyPatientCHCount(true, fromDate));
                map.put("chNGbReg", reportRepository.getMonthlyPatientCHCount(false, fromDate));
                map.put("nchGbReg", reportRepository.getMonthlyPatientNCHCount(true, fromDate));
                map.put("nchNGbReg", reportRepository.getMonthlyPatientNCHCount(false, fromDate));
            }else{
                map.put("chGbReg", reportRepository.getMonthlyPatientCHCount(centers, true, fromDate));
                map.put("chNGbReg", reportRepository.getMonthlyPatientCHCount(centers, false, fromDate));
                map.put("nchGbReg", reportRepository.getMonthlyPatientNCHCount(centers, true, fromDate));
                map.put("nchNGbReg", reportRepository.getMonthlyPatientNCHCount(centers, false, fromDate));
            }
        }else if(type.equalsIgnoreCase("range")){
            LocalDateTime fromDateLDT = LocalDateTime.parse(fromDate);
            LocalDateTime toDateLDT = LocalDateTime.parse(toDate);
            if(centers.size()==0) {
                map.put("chGbReg", reportRepository.getRangePatientCHCount(true, fromDateLDT, toDateLDT));
                map.put("chNGbReg", reportRepository.getRangePatientCHCount(false, fromDateLDT, toDateLDT));
                map.put("nchGbReg", reportRepository.getRangePatientNCHCount(true, fromDateLDT, toDateLDT));
                map.put("nchNGbReg", reportRepository.getRangePatientNCHCount(false, fromDateLDT, toDateLDT));
            }else {
                map.put("chGbReg", reportRepository.getRangePatientCHCount(centers, true, fromDateLDT, toDateLDT));
                map.put("chNGbReg", reportRepository.getRangePatientCHCount(centers, false, fromDateLDT, toDateLDT));
                map.put("nchGbReg", reportRepository.getRangePatientNCHCount(centers, true, fromDateLDT, toDateLDT));
                map.put("nchNGbReg", reportRepository.getRangePatientNCHCount(centers, false, fromDateLDT, toDateLDT));
            }
        }

        if(centers.size()==0){
            map.put("total",reportRepository.getTotalPatientRegistrationCount());
        }else{
            map.put("total",reportRepository.getTotalPatientRegistrationCount(centers));
        }
        return map;
    }


    @Override
    public Map<String, ?> getServiceSaleStats(String regionCode, String centerCode, String type, String fromDate,
                                              String toDate) {
        List<Long> centers = getCenters(regionCode,centerCode);

        Map<String,Object> map = new HashMap<>();
        if(type.equalsIgnoreCase("daily")){
            if(centers.size()==0){
                map.put("prescription",ssReportRepository.getPrescriptionStats(fromDate));
                map.put("labtest",ssReportRepository.getLabTestStats(fromDate));
                map.put("ultrasono",ssReportRepository.getUltraSonoStats(fromDate));

            }else{
                map.put("prescription",ssReportRepository.getPrescriptionStats(centers,  fromDate));
                map.put("labtest",ssReportRepository.getLabTestStats(centers,  fromDate));
                map.put("ultrasono",ssReportRepository.getUltraSonoStats(centers, fromDate));

            }

        }else if(type.equalsIgnoreCase("monthly")){
            if(centers.size()==0) {
                map.put("prescription", ssReportRepository.getPrescriptionMonthlyStats(fromDate));
                map.put("labtest", ssReportRepository.getLabTestMonthlyStats(fromDate));
                map.put("ultrasono", ssReportRepository.getUltraSonoMonthlyStats(fromDate));

            }else{
                map.put("prescription", ssReportRepository.getPrescriptionMonthlyStats(centers, fromDate));
                map.put("labtest", ssReportRepository.getLabTestMonthlyStats(centers,fromDate));
                map.put("ultrasono", ssReportRepository.getUltraSonoMonthlyStats(centers, fromDate));

            }
        }else if(type.equalsIgnoreCase("range")){
            LocalDateTime fromDateLDT = LocalDateTime.parse(fromDate);
            LocalDateTime toDateLDT = LocalDateTime.parse(toDate);
            if(centers.size()==0) {
                map.put("prescription", ssReportRepository.getPrescriptionStats(fromDateLDT, toDateLDT));
                map.put("labtest", ssReportRepository.getLabTestStats(fromDateLDT, toDateLDT));
                map.put("ultrasono", ssReportRepository.getUltraSonoStats(fromDateLDT, toDateLDT));

            }else {
                map.put("prescription", ssReportRepository.getPrescriptionStats(centers, fromDateLDT, toDateLDT));
                map.put("labtest", ssReportRepository.getLabTestStats(centers, fromDateLDT, toDateLDT));
                map.put("ultrasono", ssReportRepository.getUltraSonoStats(centers,fromDateLDT, toDateLDT));

            }
        }
        return map;
    }
}
