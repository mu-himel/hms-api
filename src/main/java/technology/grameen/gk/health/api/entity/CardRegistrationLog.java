package technology.grameen.gk.health.api.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_registration_logs")
public class CardRegistrationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long registraionId;
    private String cardNumber;
    private LocalDateTime cardReceivedDate;
    private LocalDateTime expiredDate;
    private Boolean isActive;
    private Boolean isCardReceived;
    private LocalDateTime startDate;
    private Integer totalServiceTaken;
    private Integer validityDuration;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;
}
