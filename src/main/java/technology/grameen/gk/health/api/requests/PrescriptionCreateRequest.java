package technology.grameen.gk.health.api.requests;

import technology.grameen.gk.health.api.entity.PatientDiseaseProfile;
import technology.grameen.gk.health.api.entity.Prescription;

public class PrescriptionCreateRequest {

    private Prescription prescription;
    private PatientDiseaseProfile patientDiseaseProfile;

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public PatientDiseaseProfile getPatientDiseaseProfile() {
        return patientDiseaseProfile;
    }

    public void setPatientDiseaseProfile(PatientDiseaseProfile patientDiseaseProfile) {
        this.patientDiseaseProfile = patientDiseaseProfile;
    }
}
