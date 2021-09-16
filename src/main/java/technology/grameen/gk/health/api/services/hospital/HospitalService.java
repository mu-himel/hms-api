package technology.grameen.gk.health.api.services.hospital;

import technology.grameen.gk.health.api.entity.Hospital;

import java.util.List;

public interface HospitalService {

    Hospital addHospital(Hospital hospital);

    List<Hospital> getHospitals();
}
