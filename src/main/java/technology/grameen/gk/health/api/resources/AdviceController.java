package technology.grameen.gk.health.api.resources;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.entity.Advice;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.services.AdviceService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/advices")
public class AdviceController {

    private static final Integer SIZE = 20;
    private AdviceService adviceService;

    public AdviceController(AdviceService adviceService){
        this.adviceService = adviceService;
    }

    @PostMapping
    public ResponseEntity<IResponse> addAdvice(@RequestBody Advice advice){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.CREATED.value(),
                adviceService.addAdvice(advice)
        ), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<IResponse> getAdvices(@RequestParam Optional<String> title){
        return new ResponseEntity<>(new EntityCollectionResponse<>(
                HttpStatus.OK.value(),
                adviceService.getAdvices(title.orElse(""))
        ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<IResponse> getAdvices(@RequestParam Optional<Integer> page,
                                                @RequestParam Optional<Integer> size,
                                                @RequestParam Optional<String> title){
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(SIZE));

        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                adviceService.getAdvices(pageable,title.orElse(""))
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IResponse> getAdviceById(@PathVariable("id") Long id){
        return new ResponseEntity<>(new EntityResponse<>(
                HttpStatus.OK.value(),
                adviceService.getAdviceById(id)
        ), HttpStatus.OK);
    }


}
