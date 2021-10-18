package technology.grameen.gk.health.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "ha_villages")
public class HaVillage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Village village;

    @ManyToOne
    private HealthCenter center;

    @ManyToOne
    private Employee healthAssistant;

    public HealthCenter getCenter() {
        return center;
    }

    public Village getVillage() {
        return village;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public void setCenter(HealthCenter center) {
        this.center = center;
    }

    public Employee getHealthAssistant() {
        return healthAssistant;
    }

    public void setHealthAssistant(Employee healthAssistant) {
        this.healthAssistant = healthAssistant;
    }
}
