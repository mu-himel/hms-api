package technology.grameen.gk.health.api.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private EventCategory eventCategory;

    private Integer regionOfficeId;

    @ManyToOne(fetch = FetchType.LAZY)
    private HealthCenter center;

    private LocalDateTime eventDate;

    private String eventType;

    private String startTime;

    private String endTime;


    @OneToMany(mappedBy = "event")
    private Set<EventPersonnel> eventPersonnels;

    @OneToMany(mappedBy = "event")
    private Set<EventLog> eventLogs;

    @OneToMany(mappedBy = "event")
    private Set<PatientInvoice> patientInvoices;

    @ManyToOne(fetch = FetchType.LAZY)
    private Village village;

    private String note;

    private String locationAddress;

    private String status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public Integer getRegionOfficeId() {
        return regionOfficeId;
    }

    public void setRegionOfficeId(Integer regionOfficeId) {
        this.regionOfficeId = regionOfficeId;
    }

    public HealthCenter getCenter() {
        return center;
    }

    public void setCenter(HealthCenter center) {
        this.center = center;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
