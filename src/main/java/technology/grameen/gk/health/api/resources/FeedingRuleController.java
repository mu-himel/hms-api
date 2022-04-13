package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.FeedingRule;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.FeedingRuleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/feeding-rules")
public class FeedingRuleController{

    private static final Integer SIZE = 20;
    private FeedingRuleService feedingRuleService;

    FeedingRuleController(FeedingRuleService feedingRuleService){
        this.feedingRuleService = feedingRuleService;
    }

    @GetMapping("/list")
    public ResponseEntity<IResponse> getAllRules(){
        return new ResponseEntity<>(
                new EntityCollectionResponse<>(HttpStatus.OK.value(),
                        feedingRuleService.getAllFeedingRules()),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<IResponse> getAllRules(@RequestParam Optional<Integer> page,
                                                 @RequestParam Optional<Integer> size){

        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(SIZE));
        return new ResponseEntity<>(
                new EntityResponse<>(HttpStatus.OK.value(),
                        feedingRuleService.getAllFeedingRules(pageable)),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IResponse> getRuleById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(
                new EntityResponse<>(
                        HttpStatus.OK.value(),
                        feedingRuleService.getById(id)
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<IResponse> addRule(@RequestBody FeedingRule rule){
        return new ResponseEntity<>(
                new EntityResponse<>(
                        HttpStatus.CREATED.value(),
                        feedingRuleService.addRule(rule)
                ),
                HttpStatus.CREATED
        );
    }
}
