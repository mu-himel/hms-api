package technology.grameen.gk.health.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "ha_homes")
public class HaHome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Village village;

    @ManyToOne
    private  Employee healthAssistant;
}
