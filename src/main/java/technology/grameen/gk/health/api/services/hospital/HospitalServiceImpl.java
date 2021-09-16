package technology.grameen.gk.health.api.services.hospital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.Hospital;
import technology.grameen.gk.health.api.repositories.lookup.HospitalRepository;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService{

    private HospitalRepository hospitalRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    @Transactional
    public Hospital addHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    @Override
    public List<Hospital> getHospitals() {
        return hospitalRepository.findAll();
    }

    @Override
    public Page<Hospital> getHospitals(String name,Pageable pageable) {
        if(name.isEmpty()){
            return hospitalRepository.findAll(pageable);
        }
        return hospitalRepository.findAllByNameContainingIgnoreCase(name,pageable);
    }
}
