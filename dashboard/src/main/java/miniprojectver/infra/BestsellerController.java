package miniprojectver.infra;

import lombok.RequiredArgsConstructor;
import miniprojectver.domain.BestsellerReadModel;
import miniprojectver.domain.BestsellerReadModelRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bestsellers")
public class BestsellerController {

    private final BestsellerReadModelRepository readRepo;

    // ✅ 전체 베스트셀러 목록 조회
    @GetMapping
    public List<BestsellerReadModel> getAll() {
        return readRepo.findAll();
    }

    // ✅ 특정 책 ID로 베스트셀러 정보 조회
    @GetMapping("/{bookId}")
    public Optional<BestsellerReadModel> getByBookId(@PathVariable String bookId) {
        return readRepo.findById(bookId);
    }
}
