package technology.grameen.gk.health.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "general_settings")
public class GeneralSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean enableEmailNotification;
    private Boolean enableSmsNotification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnableEmailNotification() {
        return enableEmailNotification;
    }

    public void setEnableEmailNotification(Boolean enableEmailNotification) {
        this.enableEmailNotification = enableEmailNotification;
    }

    public Boolean getEnableSmsNotification() {
        return enableSmsNotification;
    }

    public void setEnableSmsNotification(Boolean enableSmsNotification) {
        this.enableSmsNotification = enableSmsNotification;
    }
}
