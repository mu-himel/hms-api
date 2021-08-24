package technology.grameen.gk.health.api.projection;

import java.math.BigDecimal;

public interface MonthlyStatisticalReport {

    Long getCenterId();
    String getName();
    String getCenterCode();
    String getThirdLevel();
    Integer getGbChByDoc();
    Integer getGbNchByDoc();
    Integer getNgbChByDoc();
    Integer getNgbNchByDoc();
    Integer getGbChByDmf();
    Integer getGbNchByDmf();
    Integer getNgbChByDmf();
    Integer getNgbNchByDmf();
    Integer getGbChByParam();
    Integer getGbNchByParam();
    Integer getNgbChByParam();
    Integer getNgbNchByParam();
    Integer getSatGbChByDoc();
    Integer getSatGbNchByDoc();
    Integer getSatNgbChByDoc();
    Integer getSatNgbNchByDoc();
    Integer getSatGbChByDmf();
    Integer getSatGbNchByDmf();
    Integer getSatNgbChByDmf();
    Integer getSatNgbNchByDmf();
    Integer getSatGbChByParam();
    Integer getSatGbNchByParam();
    Integer getSatNgbChByParam();
    Integer getSatNgbNchByParam();
    Integer getTeleCallNo();
    Integer getMalePatientCount();
    Integer getFemalePatientCount();
    Integer getEyeCataractSurgery();
    Integer getMinorSurgery();
    Integer getCircumcision();
    Integer getEyeSurgeryIdentified();
    Integer getLabTestNo();
    BigDecimal getLabTestIncomeByCenter();
    BigDecimal getLabTestIncomeByCamp();
    BigDecimal getLabTestIncomeBySatellite();
    Integer getGbChHalfYearly();
    Integer getGbChYearly();
    Integer getNgbChHalfYearly();
    Integer getNgbChYearly();

}
