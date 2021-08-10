package technology.grameen.gk.health.api.projection;

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

}
