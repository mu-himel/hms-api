package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.Advice;

import java.util.List;
import java.util.Optional;

public interface AdviceService {

    Advice addAdvice(Advice advice);

    Page<Advice> getAdvices(Pageable pageable, String title);

    List<Advice> getAdvices(String title);

    Optional<?> getAdviceById(Long id);
}
