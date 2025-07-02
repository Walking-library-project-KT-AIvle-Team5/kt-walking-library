package miniprojectver.infra;

import miniprojectver.domain.BestsellerReadModel;
import miniprojectver.domain.BestsellerReadModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class BestsellerController {

    private final BestsellerReadModelRepository repository;

    @GetMapping("/bestsellers")
    public List<BestsellerReadModel> getBestsellers() {
        return repository.findAll();
    }
}
