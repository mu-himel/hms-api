package technology.grameen.gk.health.api.services.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.projection.PatientSearchResult;
import technology.grameen.gk.health.api.projection.PatientNumberAutoComplete;
import technology.grameen.gk.health.api.repositories.PatientInvoiceRepository;
import technology.grameen.gk.health.api.requests.ExistingPatientRequest;
import technology.grameen.gk.health.api.requests.PatientRequest;
import technology.grameen.gk.health.api.requests.PatientSearch;
import technology.grameen.gk.health.api.responses.PatientListItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PatientManageService {

   Patient addPatient(PatientRequest patient) throws Exception;

   Page<PatientListItem> getPatients(Pageable pageable);

   Optional<PatientSearchResult> getPatientById(Long id);

   Optional<PatientSearchResult> getPatientByPId(String id);

//   Boolean cardRegister(CardRegistration cardRegistration) throws Exception;

   Integer getMaxCardRegId();

   Patient  getReference(Long id);

   List<PatientNumberAutoComplete> getPatientIds(String pid);
   List<PatientNumberAutoComplete> getPatientIds(String type,String pid);
   List<PatientNumberAutoComplete> getPatientIds(String type,String pid,Long centerId);


   Page<PatientListItem> getPatientsBySearch(Long centerId, String field, String value, Pageable pageable);

   Optional<BigDecimal> getTotalAmount(HealthCenter center, String fromDate, String toDate);
   Optional<BigDecimal> getTotalAmountUptoLastDay(HealthCenter center,  String toDate);

   Integer getAllPatientCount(HealthCenter center, String fromDate, String toDate);
   Integer getGbPatientCount(HealthCenter center, String fromDate, String toDate);
   Integer getNonGbPatientCount(HealthCenter center, String fromDate, String toDate);

   Integer getAllPatientCountUpToLastDay(HealthCenter center, String toDate);
   Integer getGbPatientCountUpToLastDay(HealthCenter center, String toDate);
//   Integer getNonGbPatientCount(HealthCenter center, String fromDate, String toDate);

   PatientInvoiceRepository getInvoiceRepository();

   Patient addPatient(ExistingPatientRequest patient) throws Exception;

   Optional<Integer> hasPatient(String fullName, String motherName, String villageId);
}
