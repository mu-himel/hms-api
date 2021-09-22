package technology.grameen.gk.health.api.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "disease_types")
public class DiseaseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String alias;

    public DiseaseType(){}

    public DiseaseType(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "diseaseType")
    private Set<DiseaseProfile> diseaseProfiles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
