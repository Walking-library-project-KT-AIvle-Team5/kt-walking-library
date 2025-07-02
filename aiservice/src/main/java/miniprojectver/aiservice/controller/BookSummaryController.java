package miniprojectver.aiservice.controller;

import miniprojectver.aiservice.service.TextSummarizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class BookSummaryController {

    @Autowired
    private TextSummarizationService summarizationService;

    @PostMapping("/summarize")
    public String summarizeText(@RequestBody Map<String, String> payload) {
        String text = payload.get("text");
        return summarizationService.summarize(text);
    }
}
