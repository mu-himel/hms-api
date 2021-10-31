package technology.grameen.gk.health.api.services;

import technology.grameen.gk.health.api.entity.HaPatientService;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.requests.HaPatientServiceSell;

import java.time.LocalDateTime;
import java.util.List;

public interface IHaPatientService {

    HaPatientService addHaPatientService(HaPatientServiceSell haPatientService) throws Exception;

    List<?> getPatientServicesByDate(Long patientId, String date);
}
