package technology.grameen.gk.health.api.requests;

import technology.grameen.gk.health.api.entity.HaPatientService;
import technology.grameen.gk.health.api.entity.Patient;

public class HaPatientServiceSell {

    private HaPatientService haPatientService;

    private Patient patient;

    public HaPatientService getHaPatientService() {
        return haPatientService;
    }

    public void setHaPatientService(HaPatientService haPatientService) {
        this.haPatientService = haPatientService;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
