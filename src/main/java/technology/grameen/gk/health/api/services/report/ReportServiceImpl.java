package technology.grameen.gk.health.api.services.report;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.projection.MonthWiseReceived;
import technology.grameen.gk.health.api.projection.MonthlyStatisticalReport;
import technology.grameen.gk.health.api.projection.ServiceRecord;
import technology.grameen.gk.health.api.projection.event.schedule.Event;
import technology.grameen.gk.health.api.projection.event.schedule.EventCategory;
import technology.grameen.gk.health.api.projection.event.schedule.HCenter;
import technology.grameen.gk.health.api.repositories.EventRepository;
import technology.grameen.gk.health.api.repositories.ReportRepository;
import technology.grameen.gk.health.api.repositories.ServiceRecordRepository;
import technology.grameen.gk.health.api.requests.ServiceRecordSearch;
import technology.grameen.gk.health.api.responses.ServiceRecordResponse;
import technology.grameen.gk.health.api.services.HealthCenterService;
import technology.grameen.gk.health.api.services.PatientManageService;
import technology.grameen.gk.health.api.services.event.EventService;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService{

    ServiceRecordRepository serviceRecordRepository;
    PatientManageService patientManageService;
    HealthCenterService healthCenterService;
    EventService eventService;
    ReportRepository reportRepository;
    SchoolVisitReportService schoolVisitReportService;


    ReportServiceImpl(ServiceRecordRepository serviceRecordRepository,
                      PatientManageService patientManageService,
                      HealthCenterService healthCenterService,
                      EventService eventService,
                      ReportRepository reportRepository,
                      SchoolVisitReportService schoolVisitReportService){
        this.serviceRecordRepository = serviceRecordRepository;
        this.patientManageService = patientManageService;
        this.healthCenterService = healthCenterService;
        this.eventService = eventService;
        this.reportRepository = reportRepository;
        this.schoolVisitReportService = schoolVisitReportService;
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
        report.put("stats",reportRepository.getMonthlyStatisticalReport(yearMonth,regionCode));
        report.put("campNo",reportRepository.getCampNoCenterWiseEvent(regionCode));
        report.put("cardMember",reportRepository.getCardMemberCenterWiseEvent(regionCode));
        report.put("nonCardMember",reportRepository.getNonCardMemberCenterWiseEvent(regionCode));
        report.put("usgCampNo",reportRepository.getCampNo("usg",regionCode));
        report.put("usgCardMember",reportRepository.getCardMemberCount("usg",regionCode));
        report.put("usgNonCardMember",reportRepository.getNonCardMemberCount("usg",regionCode));
        report.put("xRayCampNo",reportRepository.getCampNo("x-ray",regionCode));
        report.put("xRayCardMember",reportRepository.getCardMemberCount("x-ray",regionCode));
        report.put("xRayNonCardMember",reportRepository.getNonCardMemberCount("x-ray",regionCode));

        report.put("schoolVisitCampNo",schoolVisitReportService.getSchoolVisitCampNo(regionCode,yearMonth));
        report.put("schoolVisitPatientNo",schoolVisitReportService.getSchoolVisitPatientNo(regionCode,yearMonth));
        report.put("schoolVisitBloodGrouping",schoolVisitReportService.getSchoolVisitServiceCount("blood",
                regionCode,yearMonth));
        report.put("incomeStats",reportRepository.getCenterAndSatelliteIncomes(regionCode,yearMonth));
        report.put("campIncomeStats",reportRepository.getCenterCampIncomes(regionCode,yearMonth));
        return report;
    }
}
