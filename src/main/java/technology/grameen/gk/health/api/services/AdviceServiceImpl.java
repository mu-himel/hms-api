package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.Advice;
import technology.grameen.gk.health.api.repositories.AdviceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdviceServiceImpl implements AdviceService{

    private AdviceRepository adviceRepository;

    public AdviceServiceImpl(AdviceRepository adviceRepository){
        this.adviceRepository = adviceRepository;
    }

    @Override
    @Transactional
    public Advice addAdvice(Advice advice) {
        return adviceRepository.save(advice);
    }

    @Override
    public Page<Advice> getAdvices(Pageable pageable, String title) {
        if(title.isEmpty()){
            return adviceRepository.findAll(pageable);
        }
        return adviceRepository.findAllByTitle(title, pageable);
    }

    @Override
    public List<Advice> getAdvices(String title) {
        if(title.isEmpty()){
            return adviceRepository.findAll();
        }
        return adviceRepository.findAllByTitle(title);
    }

    @Override
    public Optional<?> getAdviceById(Long id) {
        return adviceRepository.findById(id);
    }
}
