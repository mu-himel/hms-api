package technology.grameen.gk.health.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.FeedingRule;
import technology.grameen.gk.health.api.repositories.FeedingRuleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FeedingRuleServiceImpl implements FeedingRuleService {

    private FeedingRuleRepository feedingRuleRepository;

    FeedingRuleServiceImpl(FeedingRuleRepository feedingRuleRepository){
        this.feedingRuleRepository = feedingRuleRepository;
    }

    @Override
    public List<FeedingRule> getAllFeedingRules() {
        return feedingRuleRepository.findAll();
    }

    @Override
    public Optional<?> getById(Integer id) {
        return feedingRuleRepository.findById(id);
    }

    @Override
    @Transactional
    public FeedingRule addRule(FeedingRule rule) {
        return feedingRuleRepository.save(rule);
    }

    @Override
    public Page<FeedingRule> getAllFeedingRules(Pageable pageable) {
        return feedingRuleRepository.findAll(pageable);
    }
}
