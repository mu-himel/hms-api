package technology.grameen.gk.health.api.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Table(name = "business_targets")
public class BusinessTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String yearMonth;

    @ManyToOne(fetch = FetchType.LAZY)
    private HealthCenter createdOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    private HealthCenter forOffice;

    @PositiveOrZero
    private Integer hcPatientPerDay;

    @PositiveOrZero
    private Integer satPatientPerDay;

    @PositiveOrZero
    private Double hcIncomePerDay;

    @PositiveOrZero
    private Double satIncomePerDay;

    private Long createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
