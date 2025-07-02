package miniprojectver.controller;

import lombok.RequiredArgsConstructor;
import miniprojectver.domain.BestsellerReadModel;
import miniprojectver.domain.BestsellerReadModelRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class BestsellerTestController {

    private final BestsellerReadModelRepository repository;

    @PostMapping("/bestseller")
    public String sendBestsellerEvent(@RequestBody BestsellerReadModel dto) {
        new miniprojectver.domain.BestsellerRegistered(dto.getBookId()).publish();
        return "✅ BestsellerRegistered 이벤트 전송됨: bookId = " + dto.getBookId();
    }

    @GetMapping("/bestseller/{bookId}")
    public Optional<BestsellerReadModel> checkBook(@PathVariable Long bookId) {
        return repository.findById(bookId);
    }
}
