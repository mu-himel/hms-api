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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{

    private EventRepository eventRepository;
    private EventPersonnelService eventPersonnelService;

    public EventServiceImpl(EventRepository eventRepository, EventPersonnelService eventPersonnelService) {
        this.eventRepository = eventRepository;
        this.eventPersonnelService = eventPersonnelService;
    }

    @Override
    public Page<EventRepository.EventItem> getEvents(
                                                     String centerId,
                                                     String eventCategoryId,
                                                     String eventType,
                                                     String doctor,
                                                     String fromDate,
                                                     String toDate,
                                                     Pageable pageable) {

        if(centerId.isEmpty() && eventCategoryId.isEmpty() && eventType.isEmpty() &&
        doctor.isEmpty() && fromDate.isEmpty() && toDate.isEmpty()){
            return eventRepository.findAllEvents(pageable);
        }

        if(!fromDate.isEmpty() && !toDate.isEmpty()) {
            LocalDateTime _fromDate = LocalDateTime.parse(fromDate);
            LocalDateTime _toDate = LocalDateTime.parse(toDate);
            return eventRepository.findAllByfilter(centerId,eventCategoryId,
                    eventType,doctor,_fromDate,_toDate,pageable);
        }


        return eventRepository.findAllByfilter(centerId,eventCategoryId,
                                    eventType,doctor,pageable);

    }

    @Override
    @Transactional
    public Event addEvent(EventRequest er) throws CustomException {
        Event event = er.getEvent();

        List<EventRepository.EventLite> hasEvent = new ArrayList<>();
        if(event.getId()==null) {
            hasEvent = hasEventOnCenterAt(event.getCenter(), event.getEventDate());
            if (hasEvent.size() > 0) {
                throw new CustomException("Sorry! Event exist on the date");
            }

            hasEvent = hasEventForDoctorAt(er.getEventPersonnel().getEmployee(), event.getEventDate());
            if(hasEvent.size()>0){
                throw new CustomException("Sorry! This Doctor has schedule on this date");
            }
        }


        event.setRegionOfficeId(er.getRegionOfficeId());
        EventPersonnel eventPersonnel = er.getEventPersonnel();
        eventRepository.save(event);
        eventPersonnel.setEvent(event);
        eventPersonnelService.addEventPersonnel(eventPersonnel);
        return event;
    }

    @Override
    public List<EventRepository.EventLite> hasEventOnCenterAt(HealthCenter center, LocalDateTime eventDate) {
        return eventRepository.findByCenterAndEventDate(center, eventDate);
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
    public Optional<EventRepository.LiveEvent> hasCampEventOnCenterAt(HealthCenter center, LocalDateTime eventDate) {
        return eventRepository.findByEventTypeAndCenterAndEventDate(EventService.CAMP,center,eventDate);
    }
}
