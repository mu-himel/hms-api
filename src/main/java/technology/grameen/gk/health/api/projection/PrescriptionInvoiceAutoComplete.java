package technology.grameen.gk.health.api.projection;

public interface PrescriptionInvoiceAutoComplete{
    Long getId();
    String getInvoiceNumber();
    String getPatientFullName();
    String getPid();
    String getInvoiceType();
    Long getEventId();
}
