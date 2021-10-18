package technology.grameen.gk.health.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "ha_homes")
public class HaHome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String homeName;

    @ManyToOne
    private Village village;

    @ManyToOne
    private  Employee healthAssistant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public Employee getHealthAssistant() {
        return healthAssistant;
    }

    public void setHealthAssistant(Employee healthAssistant) {
        this.healthAssistant = healthAssistant;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }
}
