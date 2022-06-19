package technology.grameen.gk.health.api.services.medicine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.MedicineType;
import technology.grameen.gk.health.api.exceptions.CustomException;


import java.util.List;
import java.util.Optional;

public interface MedicineTypeService {

    void addMedicineType(MedicineType medicineType) throws CustomException;

    void addMedicineType(Long id, MedicineType medicineType) throws CustomException;

    List<MedicineType> getByName(String name);

    Page<MedicineType> getPage(Pageable pageable);


    Optional<MedicineType> getById(Long id);
}
