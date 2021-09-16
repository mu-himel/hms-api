package technology.grameen.gk.health.api.services.hospital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalService {

    Hospital addHospital(Hospital hospital);

    List<Hospital> getHospitals();

    Page<Hospital> getHospitals(String name,Pageable pageable);

    Optional<Hospital> getById(Long id);
}
