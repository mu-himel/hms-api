package technology.grameen.gk.health.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service_categories",
uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class ServiceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OrderBy("ServiceCategory.name desc")
    private String name;
    private String alias;
    private String description;
    private Boolean isActive;
    private Boolean isLabTest;

    @OneToMany(mappedBy = "serviceCategory")
    private Set<Service> services;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;

    public ServiceCategory(){}

    public ServiceCategory(Long id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getLabTest() {
        return isLabTest;
    }

    public void setLabTest(Boolean labTest) {
        isLabTest = labTest;
    }

    @JsonIgnore
    public Set<Service> getServices() {
        return services;
    }

    public void addServices(Service service) {
        if(service != null){
            if(services == null){
                this.services = new HashSet<>();
            }
            service.setServiceCategory(this);
            this.services.add(service);

        }

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
