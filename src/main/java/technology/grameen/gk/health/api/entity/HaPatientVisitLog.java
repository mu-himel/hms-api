package technology.grameen.gk.health.api.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ha_patient_visit_logs")
public class HaPatientVisitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer bpDiastolic;
    private Integer bpSystolic;

    private Integer height;
    private Integer weight;
    private Integer noOfFamilyMember;

    @ManyToOne
    private DiseaseProfile diseaseProfile;

    private Boolean isTakingMedicine;
    private Boolean isDoingCounseling;

    private Boolean adviceToGoAtHealthCenter;
    private Boolean adviceToGoAtSatellite;
    private Boolean adviceToGoAtCamp;

    @ManyToOne
    private Employee healthAssistant;

    @ManyToOne
    private Patient patient;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
