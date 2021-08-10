package technology.grameen.gk.health.api.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "monthly_statistical_center_wise_view")
public class MonthlyStatisticalCenterWiseView {
    @Id
    Long centerId;

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }
}
