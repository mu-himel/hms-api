package technology.grameen.gk.health.api.services.medicine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.MedicineBrand;
import technology.grameen.gk.health.api.repositories.lookup.MedicineBrandRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineBrandServiceImpl implements MedicineBrandService{

    private MedicineBrandRepository medicineBrandRepository;

    MedicineBrandServiceImpl(MedicineBrandRepository medicineBrandRepository){
        this.medicineBrandRepository = medicineBrandRepository;
    }

    @Override
    public List<MedicineBrand> getList() {
        return this.medicineBrandRepository.findAll();
    }

    @Override
    @Transactional
    public MedicineBrand addMedicineBrand(MedicineBrand medicineBrand) {
        if(medicineBrand.getId()!=null){
            Optional<MedicineBrand> optMedicine = medicineBrandRepository.findById(medicineBrand.getId());
            if(optMedicine.isPresent()){
                return medicineBrandRepository.save(medicineBrand);
            }else{
                return null;
            }

        }
        return medicineBrandRepository.save(medicineBrand);
    }

    @Override
    public Page<MedicineBrand> getBrands(String name, Pageable pageable) {
        if(name.isEmpty()){
            return medicineBrandRepository.findAll(pageable);
        }
        return medicineBrandRepository.findAllByNameContainingIgnoreCase(name,pageable);
    }

    @Override
    public Optional<MedicineBrand> getBrandById(Long id) {
        return medicineBrandRepository.findById(id);
    }
}
