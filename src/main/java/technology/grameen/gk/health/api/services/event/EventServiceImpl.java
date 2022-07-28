package technology.grameen.gk.health.api.services.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.Event;
import technology.grameen.gk.health.api.entity.EventPersonnel;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.repositories.EventRepository;
import technology.grameen.gk.health.api.requests.EventRequest;
import technology.grameen.gk.health.api.services.HealthCenterService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{

    private EventRepository eventRepository;
    private EventPersonnelService eventPersonnelService;
    private HealthCenterService healthCenterService;

    public EventServiceImpl(EventRepository eventRepository,
                            EventPersonnelService eventPersonnelService,
                            HealthCenterService healthCenterService) {
        this.eventRepository = eventRepository;
        this.eventPersonnelService = eventPersonnelService;
        this.healthCenterService = healthCenterService;
    }

    @Override
    public Page<EventRepository.EventItem> getEvents(
            String regionCode, String centerId,
            String eventCategoryId,
            String eventType,
            String doctor,
            String fromDate,
            String toDate,
            Pageable pageable) {

        String type = null;

        if(eventType.equalsIgnoreCase(EventService.CAMP)){
            type = "main";
        }



        if(regionCode.isEmpty() && centerId.isEmpty() && eventCategoryId.isEmpty() && eventType.isEmpty() &&
        doctor.isEmpty() && fromDate.isEmpty() && toDate.isEmpty()){
            return eventRepository.findAllEvents(type,pageable);
        }



        if(!fromDate.isEmpty() && !toDate.isEmpty()) {
            LocalDateTime _fromDate = LocalDateTime.parse(fromDate);
            LocalDateTime _toDate = LocalDateTime.parse(toDate);
            if(!regionCode.isEmpty() && centerId.isEmpty()){
                List<Long> centerIds = healthCenterService.getCenterIdByThirdLevel(regionCode);
                return eventRepository.findAllByfilter(centerIds,eventCategoryId,
                        eventType,doctor,_fromDate,_toDate,type,pageable);
            }
            return eventRepository.findAllByfilter(centerId,eventCategoryId,
                    eventType,doctor,_fromDate,_toDate,type,pageable);
        }

        if(!regionCode.isEmpty() && centerId.isEmpty()){
            List<Long> centerIds = healthCenterService.getCenterIdByThirdLevel(regionCode);
            return eventRepository.findAllByfilter(centerIds,eventCategoryId,
                    eventType,doctor,type,pageable);
        }
        return eventRepository.findAllByfilter(centerId,eventCategoryId,
                                    eventType,doctor,type,pageable);

    }

    @Override
    @Transactional
    public Event addEvent(EventRequest er) throws CustomException {
        Event event = er.getEvent();

        List<EventRepository.LiveEvent> hasEvent = new ArrayList<>();
        if(event.getId()==null) {
            if (event.getEventType() == EventService.CAMP){
                    hasEvent = hasCampEventOnCenterAt(event.getCenter(), event.getEventDate());
                    if (hasEvent.size() > 0) {
                        throw new CustomException("Sorry! Event exist on the date");
                    }
            }

            if(event.getEventType().equalsIgnoreCase(EventService.SATELLITE)){
                hasEvent = hasCampEventOnCenterAt(event.getCenter(), event.getEventDate());
                EventRepository.LiveEvent liveEvent = hasEvent.get(0);
                if(liveEvent.getEventType() == EventService.CAMP && (liveEvent.getEventCategory().getName()
                        .equalsIgnoreCase("usg") || liveEvent.getEventCategory().getName()
                        .equalsIgnoreCase("gynae"))){
                    throw new CustomException("Sorry! USG Event exist on the date");
                }
            }

            if(er.getEvent().getEventType() == EventService.CAMP && er.getEventPersonnel().size()==0){
                throw new CustomException("Sorry! Event Personnel not found");
            }
            for(EventPersonnel ep : er.getEventPersonnel()) {
                List<EventRepository.EventLite> docEvent = hasEventForDoctorAt(ep.getEmployee(), event.getEventDate());
                if (docEvent.size() > 0) {
                    throw new CustomException("Sorry! This Doctor has schedule on this date");
                }
            }
        }


        event.setRegionOfficeId(er.getRegionOfficeId());

        eventRepository.save(event);
        er.getEventPersonnel().stream().forEach(ep->{
            ep.setEvent(event);
            eventPersonnelService.addEventPersonnel(ep);
        });


        return event;
    }

    @Override
    public List<EventRepository.EventLite> hasEventOnCenterAt(HealthCenter center, LocalDateTime eventDate) {
        return eventRepository.findByCenterAndEventDateAndEventType(center, eventDate,EventService.CAMP);
    }

    @Override
    public List<EventRepository.EventLite> hasEventForDoctorAt(Employee doctor, LocalDateTime eventDate) {
        return eventRepository.hasSchedule(doctor, eventDate);
    }

    @Override
    public Optional<EventRepository.EventDetail> getEventById(Long id) {
        return eventRepository.findEventById(id);
    }

    @Override
    public List<EventRepository.EventSchedule> getEventSchedule(String raCode, String yearMonth) {
        return eventRepository.findCampScheduleByMonth(raCode,yearMonth);
    }

    @Override
    public List<EventRepository.EventSchedule> getSatelliteSchedule(String raCode, String yearMonth) {
        return eventRepository.findSatelliteScheduleByMonth(raCode,yearMonth);
    }

    @Override
    public List<EventRepository.LiveEvent> hasCampEventOnCenterAt(HealthCenter center, LocalDateTime eventDate) {
        return eventRepository.findAllByEventTypeAndCenterAndEventDateAndStatus(EventService.CAMP,center,eventDate,EventService.APPROVED);
    }

    @Override
    public Optional<EventRepository.EventEventEmployeeByInvoice> getEventByInvoiceId(Long id) {
        return eventRepository.findByInvoiceId(id);
    }
}
