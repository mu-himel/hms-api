package technology.grameen.gk.health.api.requests;

import technology.grameen.gk.health.api.entity.Event;
import technology.grameen.gk.health.api.entity.EventPersonnel;

import java.util.List;

public class EventRequest {
    private Event event;
    private List<EventPersonnel> eventPersonnel;
    private Integer regionOfficeId;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<EventPersonnel> getEventPersonnel() {
        return eventPersonnel;
    }

    public void setEventPersonnel(List<EventPersonnel> eventPersonnel) {
        this.eventPersonnel = eventPersonnel;
    }

    public Integer getRegionOfficeId() {
        return regionOfficeId;
    }

    public void setRegionOfficeId(Integer regionOfficeId) {
        this.regionOfficeId = regionOfficeId;
    }
}
