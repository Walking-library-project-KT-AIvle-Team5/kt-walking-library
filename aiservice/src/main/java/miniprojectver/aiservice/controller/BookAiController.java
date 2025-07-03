package miniprojectver.aiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import miniprojectver.aiservice.service.TextSummarizationService;
import miniprojectver.aiservice.service.BookCategoryClassificationService;
import miniprojectver.aiservice.service.PriceCalculationService;
import miniprojectver.aiservice.controller.dto.BookCategoryRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ai")
public class BookAiController {

    @Autowired
    private TextSummarizationService summarizationService;

    @Autowired
    private BookCategoryClassificationService categoryClassificationService;

    @Autowired
    private PriceCalculationService priceCalculationService;

    @PostMapping("/summarize")
    public String summarizeText(@RequestBody String text) {
        return summarizationService.summarize(text);
    }

    @PostMapping("/classify-category")
    public String classifyCategory(@RequestBody BookCategoryRequest request) {
        return categoryClassificationService.classifyCategory(request.getTitle(), request.getAuthor(), request.getSummary());
    }

    @PostMapping("/calculate-price")
    public String calculatePrice() {
        return priceCalculationService.calculatePrice();
    }
}
