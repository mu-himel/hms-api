package technology.grameen.gk.health.api.services.medicine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.Medicine;
import technology.grameen.gk.health.api.entity.MedicineBrand;
import technology.grameen.gk.health.api.entity.MedicineGroup;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.repositories.lookup.MedicineBrandRepository;
import technology.grameen.gk.health.api.repositories.lookup.MedicineGroupRepository;
import technology.grameen.gk.health.api.repositories.lookup.MedicineRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService{

    MedicineRepository medicineRepository;
    MedicineBrandRepository medicineBrandRepository;
    MedicineGroupRepository medicineGroupRepository;

    public MedicineServiceImpl(MedicineRepository medicineRepository,
                               MedicineBrandRepository medicineBrandRepository,
                               MedicineGroupRepository medicineGroupRepository) {

        this.medicineRepository = medicineRepository;
        this.medicineBrandRepository = medicineBrandRepository;
        this.medicineGroupRepository = medicineGroupRepository;
    }

    @Override
    @Transactional
    public Medicine addMedicine(Medicine medicine) throws CustomException {
        if(medicine.getId()!=null){

            Optional<MedicineGroup> optionalMedicineGroup = medicineGroupRepository.findById(medicine
                                        .getMedicineGroup().getId());

            Optional<MedicineBrand> optionalMedicineBrand = medicineBrandRepository.findById(medicine
                    .getMedicineBrand().getId());

            if(!optionalMedicineGroup.isPresent()){
                throw new CustomException("Medicine Group not available in Health App");
            }

            if(!optionalMedicineBrand.isPresent()){
                throw new CustomException("Medicine Brand not available in Health App");
            }

            Optional<Medicine> optMedicine = medicineRepository.findById(medicine.getId());
            if(optMedicine.isPresent()){
                return medicineRepository.save(medicine);
            }else{
                return null;
            }
        }
        medicineRepository.save(medicine);
        return medicine;
    }

    @Override
    public List<Medicine> getMedicines() {
        return medicineRepository.findAll();
    }



    @Override
    public Page<Medicine> getMedicines(String medicineName, Pageable pageable) {
        if(medicineName.isEmpty()){
            return medicineRepository.findAll(pageable);
        }
        return medicineRepository.findAllByNameContainingIgnoreCase(medicineName,pageable);
    }

    @Override
    public List<Medicine> getMedicines(String medicineName) {
        return medicineRepository.findAllByNameContainingIgnoreCase(medicineName);
    }

    @Override
    public Optional <Medicine> findById(Long id) {
        return medicineRepository.findById(id);
    }
}
