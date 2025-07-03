package miniprojectver.aiservice.controller;

import miniprojectver.aiservice.controller.dto.BookCategoryRequest;
import miniprojectver.aiservice.service.BookCategoryClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookCategoryController {

    private final BookCategoryClassificationService bookCategoryClassificationService;

    @Autowired
    public BookCategoryController(BookCategoryClassificationService bookCategoryClassificationService) {
        this.bookCategoryClassificationService = bookCategoryClassificationService;
    }

    /**
     * 책 제목, 저자, 요약을 기반으로 카테고리를 분류하는 endpoint
     */
    @PostMapping("/classify")
    public ResponseEntity<String> classify(@RequestBody BookCategoryRequest request) {
        String category = bookCategoryClassificationService.classifyCategory(
                request.getTitle(),
                request.getAuthor(),
                request.getSummary()
        );
        return ResponseEntity.ok(category);
    }
}
