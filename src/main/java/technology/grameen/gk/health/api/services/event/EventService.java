package technology.grameen.gk.health.api.services.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.Event;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.repositories.EventRepository;
import technology.grameen.gk.health.api.requests.EventRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService {

    final static String CAMP="camp";
    final static String SATELLITE="satellite";
    final static String SCHOOL="school";
    final static String APPROVED = "approved";

    Event addEvent(EventRequest event) throws CustomException;

    List<EventRepository.EventLite> hasEventOnCenterAt(HealthCenter center, LocalDateTime eventDate,
                                                       String eventType);
    List<EventRepository.LiveEvent> hasCampEventOnCenterAt(HealthCenter center, LocalDateTime eventDate);

    List<EventRepository.EventLite> hasEventForDoctorAt(Employee doctor, LocalDateTime eventDate);

    Page<EventRepository.EventItem> getEvents(String regionCode, String centerId,
                                              String eventCategoryId,
                                              String eventType,
                                              String doctor,
                                              String fromDate,
                                              String toDate,
                                              Pageable pageable);

    Optional<EventRepository.EventDetail> getEventById(Long id);

    List<EventRepository.EventSchedule> getEventSchedule(String raCode, String yearMonth);

    List<EventRepository.EventSchedule> getSatelliteSchedule(String raCode, String yearMonth);

    Optional<EventRepository.EventEventEmployeeByInvoice> getEventByInvoiceId(Long id);
}
