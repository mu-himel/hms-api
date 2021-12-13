package technology.grameen.gk.health.api.requests;

import technology.grameen.gk.health.api.entity.BusinessTarget;

import java.util.List;

public class BusinessTargetRequest {

    private List<BusinessTarget> businessTargets;

    public List<BusinessTarget> getBusinessTargets() {
        return businessTargets;
    }

    public void setBusinessTargets(List<BusinessTarget> businessTargets) {
        this.businessTargets = businessTargets;
    }

}
