package technology.grameen.gk.health.api.services.medicine;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.MedicineType;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.repositories.lookup.MedicineTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineTypeServiceImpl implements MedicineTypeService{

    MedicineTypeRepository medicineTypeRepository;

    MedicineTypeServiceImpl(MedicineTypeRepository medicineTypeRepository){
        this.medicineTypeRepository = medicineTypeRepository;
    }

    @Override
    @Transactional
    public void addMedicineType(MedicineType medicineType) {
        this.medicineTypeRepository.save(medicineType);
    }

    @Override
    public List<MedicineType> getByName(String name) {
        return medicineTypeRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Page<MedicineType> getPage(Pageable pageable) {
        return medicineTypeRepository.findAll(pageable);
    }

    @Override
    public Optional<MedicineType> getById(Long id) {
        return medicineTypeRepository.findById(id);
    }

    @Override
    @Transactional
    public void addMedicineType(Long id, MedicineType medicineType) throws CustomException {
        Optional<MedicineType> medicineTypeOptional = medicineTypeRepository.findById(id);
        if(medicineTypeOptional.isPresent()) {
            Optional<MedicineType> medicineType1 = medicineTypeRepository.findByName(medicineType.getName());
            if(!id.equals(medicineType1.get().getId())){
                throw new CustomException("Sorry! Name already exist");
            }
            this.medicineTypeRepository.save(medicineType);
        }
    }
}
