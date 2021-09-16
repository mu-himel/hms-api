package technology.grameen.gk.health.api.services.hospital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.Hospital;

import java.util.List;

public interface HospitalService {

    Hospital addHospital(Hospital hospital);

    List<Hospital> getHospitals();

    Page<Hospital> getHospitals(String name,Pageable pageable);
}
