package technology.grameen.gk.health.api.repositories;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.Event;
import technology.grameen.gk.health.api.entity.EventPersonnel;
import technology.grameen.gk.health.api.entity.HealthCenter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query(value = "SELECT e FROM Event e JOIN FETCH e.eventCategory ec " +
            " JOIN FETCH e.eventPersonnels ep" +
            " JOIN FETCH ep.employee emp " +
            "WHERE emp = :doctor and e.eventDate = :eventDate")
    List<EventLite> hasSchedule(Employee doctor, LocalDateTime eventDate);

    @Query(value = "SELECT e.id, hc.NAME as center , e.event_date as eventDate, ec.NAME as eventCategory , " +
            "e.EVENT_TYPE as eventType, e2.FULL_NAME as doctor, e.status FROM Events e\n" +
            "JOIN EVENT_PERSONNEL ep ON ep.EVENT_ID = e.ID \n" +
            "LEFT JOIN EMPLOYEES e2 ON e2.ID  = ep.EMPLOYEE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "JOIN LG_VILLAGES lv ON lv.LG_VILLAGE_ID  = e.VILLAGE_LG_VILLAGE_ID " +
            "WHERE (:centerId IS NULL OR e.center_id = :centerId) " +
            "AND (:eventCategoryId IS NULL OR e.event_category_id = :eventCategoryId) "+
            "AND (:eventType IS NULL OR e.event_type = :eventType) "+
            "AND (:doctor IS NULL OR ep.employee_id = :doctor) "+
            "AND (e.event_date BETWEEN :fromDate AND :toDate) ",
            countQuery = "SELECT count(*) FROM Events e", nativeQuery = true)
    Page<EventItem> findAllByfilter(@Param("centerId") String centerId,
                                    @Param("eventCategoryId") String eventCategoryId,
                                    @Param("eventType") String eventType,
                                    @Param("doctor") String doctor,
                                    @Param("fromDate") LocalDateTime fromDate,
                                    @Param("toDate") LocalDateTime toDate,
                                    Pageable pageable);

    @Query(value = "SELECT e.id, hc.NAME as center , e.event_date as eventDate, ec.NAME as eventCategory , " +
            "e.EVENT_TYPE as eventType, e2.FULL_NAME as doctor, e.status FROM Events e\n" +
            "JOIN EVENT_PERSONNEL ep ON ep.EVENT_ID = e.ID \n" +
            "LEFT JOIN EMPLOYEES e2 ON e2.ID  = ep.EMPLOYEE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "JOIN LG_VILLAGES lv ON lv.LG_VILLAGE_ID  = e.VILLAGE_LG_VILLAGE_ID " +
            "WHERE (:centerId IS NULL OR e.center_id = :centerId) " +
            "AND (:eventCategoryId IS NULL OR e.event_category_id = :eventCategoryId) "+
            "AND (:eventType IS NULL OR e.event_type = :eventType) "+
            "AND (:doctor IS NULL OR ep.employee_id = :doctor) ",
            countQuery = "SELECT count(*) FROM Events e", nativeQuery = true)
    Page<EventItem> findAllByfilter(@Param("centerId") String centerId,
                                    @Param("eventCategoryId") String eventCategoryId,
                                    @Param("eventType") String eventType,
                                    @Param("doctor") String doctor,
                                    Pageable pageable);

    @Query(value = "SELECT e.id, hc.NAME as center , e.event_date as eventDate, ec.NAME as eventCategory , " +
            "e.EVENT_TYPE as eventType, e2.FULL_NAME as doctor, e.status FROM Events e\n" +
            "JOIN EVENT_PERSONNEL ep ON ep.EVENT_ID = e.ID \n" +
            "LEFT JOIN EMPLOYEES e2 ON e2.ID  = ep.EMPLOYEE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "JOIN LG_VILLAGES lv ON lv.LG_VILLAGE_ID  = e.VILLAGE_LG_VILLAGE_ID " +
            "WHERE e.event_category_id = :eventCategoryId",
            countQuery = "SELECT count(*) FROM Events e", nativeQuery = true)
    Page<EventItem> findAllByEventCategory(@Param("eventCategoryId") Integer eventCategoryId, Pageable pageable);

    interface EventLite{
        Long getId();
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime getEventDate();
    }


    List<EventLite> findByCenterAndEventDate(HealthCenter center, LocalDateTime eventDate);

    interface EventItem{
            Long getId();
            String getCenter();

            @JsonFormat(pattern = "yyyy-MM-dd")
            LocalDateTime getEventDate();
            String getEventCategory();
            String getEventType();
            String getDoctor();
            String getStatus();
    }

    @Query(value = "SELECT e.id, hc.NAME as center , e.event_date as eventDate, ec.NAME as eventCategory , " +
            "e.EVENT_TYPE as eventType, e2.FULL_NAME as doctor, e.status FROM Events e\n" +
            "JOIN EVENT_PERSONNEL ep ON ep.EVENT_ID = e.ID \n" +
            "LEFT JOIN EMPLOYEES e2 ON e2.ID  = ep.EMPLOYEE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID \n" +
            "JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID \n" +
            "JOIN LG_VILLAGES lv ON lv.LG_VILLAGE_ID  = e.VILLAGE_LG_VILLAGE_ID",
    countQuery = "SELECT count(*) FROM Events e",nativeQuery = true)
    Page<EventItem> findAllEvents(Pageable pageable);

    @Query(value = "SELECT e.id, hc.NAME as center , e.event_date as eventDate, ec.NAME as eventCategory , " +
            "e.EVENT_TYPE as eventType, e2.FULL_NAME as doctor, e.status FROM Events e\n" +
            "JOIN EVENT_PERSONNEL ep ON ep.EVENT_ID = e.ID " +
            "LEFT JOIN EMPLOYEES e2 ON e2.ID  = ep.EMPLOYEE_ID " +
            "JOIN HEALTH_CENTERS hc ON hc.ID = e.CENTER_ID " +
            "JOIN EVENT_CATEGORIES ec ON ec.ID  = e.EVENT_CATEGORY_ID " +
            "JOIN LG_VILLAGES lv ON lv.LG_VILLAGE_ID  = e.VILLAGE_LG_VILLAGE_ID " +
            "WHERE e.event_Date BETWEEN :fromDate AND :toDate ",
            countQuery = "SELECT count(*) FROM Events e",nativeQuery = true)
    Page<EventItem> findAllEventsByDateRange(@Param("fromDate") LocalDateTime fromDate,
                                             @Param("toDate") LocalDateTime toDate,
                                             Pageable pageable);

    interface EventDetail{
        Long getId();
        Integer getRegionOfficeId();
        Center getCenter();
        String getLocationAddress();
        String getNote();
        Village getVillage();
        String getEventType();
        String getStatus();

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime getEventDate();

        Set<EventPersonnel> getEventPersonnels();

        EventCategory getEventCategory();

        interface EventCategory{
            Integer getId();
            String getName();
        }

        interface Village{
            Long getLgVillageId();
            String getVillageName();
            String getVillageCode();
        }
        interface EventPersonnel{
            Long getId();
            String getPersonnelType();
            Emplpoyee getEmployee();
        }
        interface Emplpoyee{
            Long getId();
            String getFullName();
        }
        interface Center {
            Long getId();
            String getName();
        }
    }
    @Query(value = "SELECT e FROM Event e JOIN FETCH e.eventCategory ec " +
            " JOIN FETCH e.eventPersonnels ep " +
            " LEFT JOIN FETCH ep.employee emp " +
            " JOIN FETCH e.center c " +
            " JOIN FETCH e.village v " +
            "WHERE e.id = :id")
    Optional<EventDetail> findEventById(@Param("id") Long id);


    interface EventSchedule{
        Long getEventId();
        Long getHcId();
        String getHcName();
        Integer getEcId();
        String getEcName();
        String getLocationAddress();

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime getEventDate();
        String getEventType();
        Long getEmpId();
        String getDoctorName();

    }
    @Query(value = "SELECT e.id as eventId, hc.id AS hcId,hc.name as hcName,ec.id AS ecId, ec.NAME as ecName," +
            "e.EVENT_DATE as eventDate ,e.EVENT_TYPE as eventType, ep.EMPLOYEE_ID AS empId, " +
            "e2.FULL_NAME as doctorName, e.location_address as locationAddress " +
            "FROM HEALTH_CENTERS hc " +
            "JOIN EVENTS e ON e.CENTER_ID = hc.id " +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID " +
            "JOIN EVENT_PERSONNEL ep ON ep.EVENT_ID =e.ID " +
            "JOIN EMPLOYEES e2 ON ep.EMPLOYEE_ID = e2.ID " +
            "WHERE e.EVENT_TYPE='camp' " +
            " AND (:raCode IS NULL OR hc.THIRD_LEVEL = :raCode) " +
            " AND (:yearMonth IS NULL OR TO_CHAR(e.event_date,'YYYY-MM') = :yearMonth) " +
            "ORDER BY hc.id ASC , ec.id ASC, e.event_date ASC",nativeQuery = true)
    List<EventSchedule> findCampScheduleByMonth(@Param("raCode") String raCode,
                                                @Param("yearMonth") String yearMonth);


    @Query(value = "SELECT e.id as eventId, hc.id as hcId, hc.name as hcName, ec.id AS ecId, " +
            "ec.NAME AS ecName, e.event_date as eventDate, e.event_type as eventType, " +
            "ep.employee_id as empId, e2.full_name as doctorName,e.location_address as locationAddress " +
            "FROM HEALTH_CENTERS hc " +
            "JOIN EVENTS e ON e.CENTER_ID = hc.id " +
            "JOIN EVENT_CATEGORIES ec ON e.EVENT_CATEGORY_ID = ec.ID " +
            "JOIN EVENT_PERSONNEL ep ON ep.EVENT_ID = e.ID " +
            "LEFT JOIN EMPLOYEES e2 ON ep.EMPLOYEE_ID = e2.ID " +
            "WHERE e.EVENT_TYPE='satellite' " +
            " AND (:raCode IS NULL OR hc.center_code = :raCode) " +
            " AND (:yearMonth IS NULL OR TO_CHAR(e.event_date,'YYYY-MM') = :yearMonth) " +
            " ORDER BY e.event_date ASC",nativeQuery = true)
    List<EventSchedule> findSatelliteScheduleByMonth(@Param("raCode") String raCode,
                                                     @Param("yearMonth") String yearMonth);
}
