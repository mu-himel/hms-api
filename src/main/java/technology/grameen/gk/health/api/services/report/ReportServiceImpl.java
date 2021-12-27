package technology.grameen.gk.health.api.services.report;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.projection.MonthWiseReceived;
import technology.grameen.gk.health.api.projection.ServiceRecord;
import technology.grameen.gk.health.api.projection.event.schedule.Event;
import technology.grameen.gk.health.api.projection.event.schedule.EventCategory;
import technology.grameen.gk.health.api.projection.event.schedule.HCenter;
import technology.grameen.gk.health.api.repositories.EventRepository;
import technology.grameen.gk.health.api.repositories.report.HaReportRepository;
import technology.grameen.gk.health.api.repositories.report.LabTestReportRepository;
import technology.grameen.gk.health.api.repositories.report.ReportRepository;
import technology.grameen.gk.health.api.repositories.ServiceRecordRepository;
import technology.grameen.gk.health.api.repositories.report.SummaryReportRepository;
import technology.grameen.gk.health.api.requests.ServiceRecordSearch;
import technology.grameen.gk.health.api.responses.ServiceRecordResponse;
import technology.grameen.gk.health.api.services.HealthCenterService;
import technology.grameen.gk.health.api.services.patient.PatientManageService;
import technology.grameen.gk.health.api.services.event.EventService;
import technology.grameen.gk.health.api.services.report.statistics.SummaryReportService;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService{

    private ServiceRecordRepository serviceRecordRepository;
    private SummaryReportService summaryReportService;
    private PatientManageService patientManageService;
    private HealthCenterService healthCenterService;
    private EventService eventService;
    private ReportRepository reportRepository;
    private SchoolVisitReportService schoolVisitReportService;
    private LabTestReportRepository labTestReportRepository;
    private HaReportRepository haReportRepository;


    ReportServiceImpl(ServiceRecordRepository serviceRecordRepository,
                      PatientManageService patientManageService,
                      SummaryReportService summaryReportService,
                      HealthCenterService healthCenterService,
                      EventService eventService,
                      ReportRepository reportRepository,
                      SchoolVisitReportService schoolVisitReportService,
                      LabTestReportRepository labTestReportRepository,
                      HaReportRepository haReportRepository){
        this.serviceRecordRepository = serviceRecordRepository;
        this.summaryReportService = summaryReportService;
        this.patientManageService = patientManageService;
        this.healthCenterService = healthCenterService;
        this.eventService = eventService;
        this.reportRepository = reportRepository;
        this.schoolVisitReportService = schoolVisitReportService;
        this.labTestReportRepository = labTestReportRepository;
        this.haReportRepository = haReportRepository;
    }


    @Override
    public List<ServiceRecord> getPatientInvoiceSummery() {
        return this.serviceRecordRepository.getServiceRecords();
    }

    @Override
    public List<ServiceRecordResponse> getPatientInvoiceSummery(ServiceRecordSearch serviceRecordSearch) {

        List<HealthCenter> centers = new ArrayList<>();
        List<Long> centerIds = new ArrayList<>();
        if(serviceRecordSearch.getOfficeTypeId() == 1 || serviceRecordSearch.getOfficeTypeId() == 4){
            centers = healthCenterService.getCenters();
        }else if(serviceRecordSearch.getOfficeTypeId()==5){
            centers = healthCenterService
                    .getCentersByThirdLevel(serviceRecordSearch
                            .getCenterCode());
        }else{
            centers = healthCenterService.getCenterById(serviceRecordSearch.getCenterId());
        }
        List<ServiceRecordResponse> serviceRecordResponses = new ArrayList<>();



        centers.stream().forEach(center->{
            centerIds.add(center.getId());
        });

        List<ServiceRecord>  serviceRecords = (List<ServiceRecord>) serviceRecordRepository.getServiceRecords(centerIds,
                serviceRecordSearch.getFromDate(),serviceRecordSearch.getToDate());

        centers.stream().forEach(center->{
            List<ServiceRecord> centerWiseRecords = new ArrayList<>();
             serviceRecords.stream()
                   .forEach((ServiceRecord sr)->{
                       Long id = sr.getHealthCenterId();
                       Long centerId = center.getId();
                       if(id.equals(centerId)){
                           centerWiseRecords.add(sr);
                       }

                   });


            if(centerWiseRecords.size()>0) {
                serviceRecordResponses.add(new ServiceRecordResponse(center, centerWiseRecords));
            }
        });
        return serviceRecordResponses;
    }

    @Override
    public PatientManageService getPatientService() {
        return patientManageService;
    }

    @Override
    public MonthWiseReceived getMonthWiseTotalAmountReceived(Long centerId) {
        Optional<HealthCenter> centerOptional = healthCenterService.findById(centerId);
        final List<Long> centerIds = new ArrayList<>();
        List<HealthCenter> centers = new ArrayList<>();

        if(centerOptional.isPresent()){
            HealthCenter center = centerOptional.get();
            if(center.getOfficeTypeId() == 1 || center.getOfficeTypeId() == 4){
                List<String> strList = healthCenterService.getCenterIds();
                Optional<String> rowOp   = strList.stream().findFirst();
                String row = rowOp.get();
                Arrays.stream(row.split(",")).forEach(m->{
                    centerIds.add(Long.valueOf(m));
                });
            }
            if(center.getOfficeTypeId() == 5){
                List<String> strList = healthCenterService.getCenterIds(center.getCenterCode());
                Optional<String> rowOp   = strList.stream().findFirst();
                String row = rowOp.get();
                Arrays.stream(row.split(",")).forEach(m->{
                    centerIds.add(Long.valueOf(m));
                });

            }
            else{
                centerIds.add(centerId);
            }
        }
        return patientManageService.getInvoiceRepository().getTotalAmountMonthWiseInCenters(centerIds);
    }

    @Override
    public List<HCenter> getEventSchedule(String raCode, String yearMonth) throws CustomException {
        if(raCode.isEmpty() || yearMonth.isEmpty()){
            throw new CustomException("Please select Region and Year month");
        }
        List<EventRepository.EventSchedule> schedules = eventService.getEventSchedule(raCode,yearMonth);
        Map<Long,Map<Integer,Map<Long,Object>>> centerIds = new HashMap<>();
        List<Integer> ecIds = new ArrayList<>();
        List<HCenter> centers = new ArrayList<>();
        HCenter hc = null;
        EventCategory ec = null;
        Event event = null;
        for(EventRepository.EventSchedule es : schedules){
            if(!centerIds.containsKey(es.getHcId())){
                centerIds.put(es.getHcId(),new HashMap<>());
                hc = new HCenter(es.getHcId(), es.getHcName());
                centers.add(hc);
                if(!centerIds.get(es.getHcId()).containsKey(es.getEcId())) {
                    centerIds.get(es.getHcId()).put(es.getEcId(),new HashMap<>());
                    ec =  new EventCategory(es.getEcId(),es.getEcName());
                    hc.addEventCategories(ec);
                    if(!centerIds.get(es.getHcId()).get(es.getEcId()).containsKey(es.getEmpId())){
                        centerIds.get(es.getHcId()).get(es.getEcId()).put(es.getEmpId(),es.getEcId());
                        event = new Event(es.getEmpId(),es.getDoctorName());
                        event.addDate(es.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        ec.addEvents(event);
                    }else{
                        Optional<Event> eventOptional = ec.getEvents().stream()
                                .filter((Event _event)->_event.getEmpId().equals(es.getEmpId())).findFirst();
                        if(eventOptional.isPresent()){
                            event = eventOptional.get();
                            event.addDate(es.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        }

                    }

                }else{
                    Optional<EventCategory> ecOptional = hc.getEventCategories().stream()
                            .filter((EventCategory _ec)->_ec.getId().equals(es.getEcId())).findFirst();
                    if(ecOptional.isPresent()){
                        ec = ecOptional.get();
                        if(!centerIds.get(es.getHcId()).get(es.getEcId()).containsKey(es.getEmpId())){
                            centerIds.get(es.getHcId()).get(es.getEcId()).put(es.getEmpId(),es.getEcId());
                            event = new Event(es.getEmpId(),es.getDoctorName());
                            event.addDate(es.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            ec.addEvents(event);
                        }else{
                            Optional<Event> eventOptional = ec.getEvents().stream()
                                    .filter((Event _event)->_event.getEmpId().equals(es.getEmpId())).findFirst();
                            if(eventOptional.isPresent()){
                                event = eventOptional.get();
                                event.addDate(es.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            }

                        }

                    }
                }
            }else{
               Optional<HCenter> hCenterOptional = centers.stream().
                                                    filter(c->c.getId().equals(es.getHcId()))
                                                    .findFirst();
               if(hCenterOptional.isPresent()){
                   hc = hCenterOptional.get();
                   if(!centerIds.get(es.getHcId()).containsKey(es.getEcId())) {
                       centerIds.get(es.getHcId()).put(es.getEcId(),new HashMap<>());
                       ec = new EventCategory(es.getEcId(),es.getEcName());
                       hc.addEventCategories(ec);
                       if(!centerIds.get(es.getHcId()).get(es.getEcId()).containsKey(es.getEmpId())){
                           centerIds.get(es.getHcId()).get(es.getEcId()).put(es.getEmpId(),es.getEcId());
                           event = new Event(es.getEmpId(),es.getDoctorName());
                           event.addDate(es.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                           ec.addEvents(event);
                       }else{
                           Optional<Event> eventOptional = ec.getEvents().stream()
                                   .filter((Event _event)->_event.getEmpId().equals(es.getEmpId())).findFirst();
                           if(eventOptional.isPresent()){
                               event = eventOptional.get();
                               event.addDate(es.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                           }

                       }
                   }else{
                       Optional<EventCategory> ecOptional = hc.getEventCategories().stream()
                                                            .filter((EventCategory _ec)->_ec.getId().equals(es.getEcId()))
                                                            .findFirst();
                       if(ecOptional.isPresent()){
                            ec = ecOptional.get();
                           if(!centerIds.get(es.getHcId()).get(es.getEcId()).containsKey(es.getEmpId())){
                               centerIds.get(es.getHcId()).get(es.getEcId()).put(es.getEmpId(),es.getEcId());
                               event = new Event(es.getEmpId(),es.getDoctorName());
                               event.addDate(es.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                               ec.addEvents(event);
                           }else{
                               Optional<Event> eventOptional = ec.getEvents().stream()
                                       .filter((Event _event)->_event.getEmpId().equals(es.getEmpId())).findFirst();
                               if(eventOptional.isPresent()){
                                   event = eventOptional.get();
                                   event.addDate(es.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                               }

                           }
                       }
                   }
               }
            }
        }
        return centers;
    }

    @Override
    public List<EventRepository.EventSchedule> getSatteliteSchedule(String raCode, String yearMonth) throws CustomException {
        if(raCode.isEmpty() || yearMonth.isEmpty()){
            throw new CustomException("Please select Region and Year month");
        }
        return eventService.getSatelliteSchedule(raCode, yearMonth);
    }

    @Override
    public Map<String,Object> getMonthlyStatisticalReport(String yearMonth,String regionCode) {
        Map<String, Object> report = new HashMap<>();
        report.put("homeVisitCount",haReportRepository.getMonthWiseHomeVisitCount(yearMonth,regionCode));
        report.put("personCheckupCount",haReportRepository.getMonthWisePersonCheckupCount(yearMonth,regionCode));
        report.put("DiabeticVisitCount",haReportRepository.getMonthWiseDiabeticVisitCount(yearMonth,regionCode));

        report.put("stats",reportRepository.getMonthlyStatisticalReport(yearMonth,regionCode));

        report.put("campNo",reportRepository.getCampNoCenterWiseEvent(yearMonth,regionCode));
        report.put("cardMember",reportRepository.getCardMemberCenterWiseEvent(yearMonth,regionCode));
        report.put("nonCardMember",reportRepository.getNonCardMemberCenterWiseEvent(yearMonth,regionCode));

        report.put("usgCampNo",reportRepository.getCampNo("usg",regionCode,yearMonth));
        report.put("usgCardMember",reportRepository.getCardMemberCount("usg",regionCode,yearMonth));
        report.put("usgNonCardMember",reportRepository.getNonCardMemberCount("usg",regionCode,yearMonth));
        report.put("xRayCampNo",reportRepository.getCampNo("x-ray",regionCode,yearMonth));
        report.put("xRayCardMember",reportRepository.getCardMemberCount("x-ray",regionCode,yearMonth));
        report.put("xRayNonCardMember",reportRepository.getNonCardMemberCount("x-ray",regionCode,yearMonth));

        report.put("schoolVisitCampNo",schoolVisitReportService.getSchoolVisitCampNo(regionCode,yearMonth));
        report.put("schoolVisitPatientNo",schoolVisitReportService.getSchoolVisitPatientNo(regionCode,yearMonth));
        report.put("schoolVisitBloodGrouping",schoolVisitReportService.getSchoolVisitServiceCount("blood",
                regionCode,yearMonth));
        report.put("incomeStats",reportRepository.getCenterAndSatelliteIncomes(regionCode,yearMonth));
        report.put("campIncomeStats",reportRepository.getCenterCampIncomes(regionCode,yearMonth));
        report.put("vaccineIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,yearMonth,"vaccine"));
        report.put("surgeryIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,yearMonth,"surgery"));
        report.put("deliveryIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,yearMonth,"mother & child care"));
        report.put("serviceRentIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,yearMonth,"rent"));
        report.put("usgIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,yearMonth,"ultrasonogram"));
        report.put("ecgIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,yearMonth,"ecg"));
        report.put("safetyNetStats",reportRepository.getSafetyNetCount(regionCode,yearMonth));
        report.put("deliveryCountStats",reportRepository.getCenterWiseDeliveryCount(regionCode,yearMonth));
        report.put("pregnantRegisterCount",reportRepository.getMonthWisePregnantRegistered(yearMonth,regionCode));
        report.put("adultVaccineCountStats",reportRepository.getCenterWiseAdultVaccinationCount(regionCode,yearMonth));
        report.put("childVaccineCountStats",reportRepository.getCenterWiseChildVaccinationCount(regionCode,yearMonth));
        report.put("referCenterCount",reportRepository.getReferCenterCount(regionCode,yearMonth));
        return report;
    }

    @Override
    public List<LabTestReportRepository.MonthlyLabTestReport> getLabTestReport(String regionCode, String type,
                                                                               String dateTime) {
        if(type.equalsIgnoreCase("monthly")) {
            return labTestReportRepository.getMonthlyLabTestReport(regionCode, dateTime);
        }else if(type.equalsIgnoreCase("daily")){
            return labTestReportRepository.getDailyLabTestReport(regionCode, dateTime);
        }else if(type.equalsIgnoreCase("center-wise-daily")){
            return labTestReportRepository.getCenterWiseDailyLabTestReport(regionCode, dateTime);
        }

        return new ArrayList<>();
    }

    @Override
    public Map<?,?> getSummaryReportStats(String type, String startDate, String endDate, String regionCode) {

        Map<String,Optional<?>> result = new HashMap<>();

        result.put("campOrganized",summaryReportService
                    .getSummaryCampOrganizedStats(type,startDate, endDate,regionCode));

        result.put("vaccineCount",summaryReportService
                    .getSummaryVaccineStats(type,startDate,endDate,regionCode));

        result.put("surgeryCount",summaryReportService
                .getSummarySurgeryStats(type,startDate,endDate,regionCode));

        result.put("ultrasonoCount",summaryReportService
                .getSummaryUltrasonoStats(type,startDate,endDate,regionCode));

        result.put("ecg",summaryReportService
                .getSummaryEcgStats(type,startDate,endDate,regionCode));

        result.put("x-ray",summaryReportService
                .getSummaryXrayStats(type,startDate,endDate,regionCode));

        return result;
    }

    @Override
    public Map<?, ?> getRangeStatisticalReport(String startDate, String endDate, String regionCode) {
        Map<String, Object> report = new HashMap<>();
        report.put("stats",reportRepository.getRangeStatisticalReport(startDate,endDate,regionCode));
        report.put("homeVisitCount",haReportRepository.getMonthWiseHomeVisitCount(startDate,endDate,regionCode));
        report.put("personCheckupCount",haReportRepository.getMonthWisePersonCheckupCount(startDate,endDate,regionCode));
        report.put("DiabeticVisitCount",haReportRepository.getMonthWiseDiabeticVisitCount(startDate,endDate,regionCode));
        report.put("campNo",reportRepository.getCampNoCenterWiseEvent(startDate,endDate,regionCode));
        report.put("cardMember",reportRepository.getCardMemberCenterWiseEvent(startDate,endDate,regionCode));
        report.put("nonCardMember",reportRepository.getNonCardMemberCenterWiseEvent(startDate,endDate,regionCode));

        report.put("usgCampNo",reportRepository.getCampNo("usg",regionCode,startDate,endDate));
        report.put("usgCardMember",reportRepository.getCardMemberCount("usg",regionCode,startDate,endDate));
        report.put("usgNonCardMember",reportRepository.getNonCardMemberCount("usg",regionCode,startDate,endDate));

        report.put("xRayCampNo",reportRepository.getCampNo("x-ray",regionCode,startDate,endDate));
        report.put("xRayCardMember",reportRepository.getCardMemberCount("x-ray",regionCode,startDate,endDate));
        report.put("xRayNonCardMember",reportRepository.getNonCardMemberCount("x-ray",regionCode,startDate,endDate));

        report.put("schoolVisitCampNo",schoolVisitReportService.getSchoolVisitCampNo(regionCode,startDate,endDate));
        report.put("schoolVisitPatientNo",schoolVisitReportService.getSchoolVisitPatientNo(regionCode,startDate,endDate));
        report.put("schoolVisitBloodGrouping",schoolVisitReportService.getSchoolVisitServiceCount("blood",
                regionCode,startDate,endDate));

        report.put("incomeStats",reportRepository.getCenterAndSatelliteIncomes(regionCode,startDate,endDate));
        report.put("campIncomeStats",reportRepository.getCenterCampIncomes(regionCode,startDate,endDate));
        report.put("vaccineIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,startDate,endDate,"vaccine"));
        report.put("surgeryIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,startDate,endDate,"surgery"));
        report.put("deliveryIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,startDate,endDate,"mother & child care"));
        report.put("serviceRentIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,startDate,endDate,"rent"));
        report.put("usgIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,startDate,endDate,"ultrasonogram"));
        report.put("ecgIncomeStats",reportRepository.getIncomeByServiceCategory(regionCode,startDate,endDate,"ecg"));

        report.put("safetyNetStats",reportRepository.getSafetyNetCount(regionCode,startDate,endDate));
        report.put("deliveryCountStats",reportRepository.getCenterWiseDeliveryCount(regionCode,startDate,endDate));

        report.put("pregnantRegisterCount",reportRepository.getDateRangeWisePregnantRegistered(startDate,endDate,regionCode));
        report.put("adultVaccineCountStats",reportRepository.getCenterWiseAdultVaccinationCount(regionCode,startDate,endDate));
        report.put("childVaccineCountStats",reportRepository.getCenterWiseChildVaccinationCount(regionCode,startDate,endDate));
        report.put("referCenterCount",reportRepository.getReferCenterCount(regionCode,startDate,endDate));

        return report;
    }

    @Override
    public Map<?, ?> getMonthWiseDeliveryReport(String lastMonth, String currentMonth) {
        Map<String, Object> report = new HashMap<>();
        report.put("pr_till_last_month",reportRepository.getPregnantRegisteredTillLastMonth(currentMonth+"-01"));
        report.put("pr_this_month",reportRepository.getPregnantRegisteredThisMonth(currentMonth));
        report.put("delivery_till_last_month",reportRepository.getDeliveryCountTillLastMonth(currentMonth+"-01"));
        report.put("delivery_last_month",reportRepository.getDeliveryCountByMonth(lastMonth));
        report.put("delivery_probability",reportRepository.getDeliveryProbabilityCount(currentMonth));
        return report;
    }
}
