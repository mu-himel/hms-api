package technology.grameen.gk.health.api.services.predefined;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.entity.Specimen;
import technology.grameen.gk.health.api.repositories.lookup.SpecimenRepository;

import java.util.List;

@Service
public class SpecimenServiceImpl implements SpecimenService {

    private SpecimenRepository repository;

    public SpecimenServiceImpl(SpecimenRepository repo){
        this.repository = repo;
    }

    @Override
    public List<Specimen> getAll() {
        return repository.findAll();
    }
}
