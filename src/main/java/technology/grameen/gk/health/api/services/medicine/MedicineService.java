package technology.grameen.gk.health.api.services.medicine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.Medicine;
import technology.grameen.gk.health.api.exceptions.CustomException;

import java.util.List;
import java.util.Optional;

public interface MedicineService {
    public Medicine addMedicine(Medicine medicine) throws CustomException;
    List<Medicine> getMedicines();

    Page<Medicine> getMedicines(String medicineName,Pageable pageable);
    List<Medicine> getMedicines(String medicineName);
    Optional <Medicine> findById(Long id);
}
