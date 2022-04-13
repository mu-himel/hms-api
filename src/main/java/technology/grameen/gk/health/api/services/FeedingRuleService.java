package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.FeedingRule;

import java.util.List;
import java.util.Optional;

public interface FeedingRuleService {

    List<FeedingRule> getAllFeedingRules();

    Optional<?> getById(Integer id);

    FeedingRule addRule(FeedingRule rule);

    Page<FeedingRule> getAllFeedingRules(Pageable pageable);
}
