package technology.grameen.gk.health.api.projection;

import technology.grameen.gk.health.api.entity.CardRegistration;

public interface PatientNumberAutoComplete {

    Long getId();
    String getPid();
    String getFullName();
    String getMobileNumber();

}
