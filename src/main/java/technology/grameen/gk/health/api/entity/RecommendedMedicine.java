package technology.grameen.gk.health.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "recommended_medicines")
public class RecommendedMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Prescription prescription;

    @ManyToOne
    private Medicine medicine;


    private String medicineType;

    private String dose;

    private String instruction;

    private String duration;

    private String durationUnit;

    private Integer rule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonBackReference
    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public Integer getRule() {
        return rule;
    }

    public void setRule(Integer rule) {
        this.rule = rule;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
