package technology.grameen.gk.health.api.services.medicine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.MedicineGroup;
import technology.grameen.gk.health.api.repositories.lookup.MedicineGroupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineGroupServiceImpl implements MedicineGroupService{

    private MedicineGroupRepository medicineGroupRepository;

    MedicineGroupServiceImpl (MedicineGroupRepository medicineGroupRepository){
        this.medicineGroupRepository = medicineGroupRepository;
    }

    @Override
    public List<MedicineGroup> getList() {
        return this.medicineGroupRepository.findAll();
    }

    @Override
    @Transactional
    public MedicineGroup addMedicineGroup(MedicineGroup medicineGroup) {
        return medicineGroupRepository.save(medicineGroup);
    }

    @Override
    public Page<MedicineGroup> getGroups(String name, Pageable pageable) {
        if(name.isEmpty()) {
            return medicineGroupRepository.findAll(pageable);
        }
        return medicineGroupRepository.findAllByNameContainingIgnoreCase( name,pageable);
    }

    @Override
    public Optional<MedicineGroup> getBrandById(Long id) {
        return medicineGroupRepository.findById(id);
    }
}
