package technology.grameen.gk.health.api.requests;

import technology.grameen.gk.health.api.entity.PatientDiseaseProfile;
import technology.grameen.gk.health.api.entity.Prescription;

import java.util.List;

public class PrescriptionCreateRequest {

    private Prescription prescription;
    private List<PatientDiseaseProfile> patientDiseaseProfile;

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public List<PatientDiseaseProfile> getPatientDiseaseProfile() {
        return patientDiseaseProfile;
    }

    public void setPatientDiseaseProfile(List<PatientDiseaseProfile> patientDiseaseProfile) {
        this.patientDiseaseProfile = patientDiseaseProfile;
    }
}
