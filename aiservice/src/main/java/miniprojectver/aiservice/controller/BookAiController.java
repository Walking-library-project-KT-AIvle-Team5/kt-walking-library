package miniprojectver.aiservice.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/ai")
public class BookAiController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("AI Service is running!", HttpStatus.OK);
    }

}

@Autowired
private TextSummarizationService textSummarizationService;

@PostMapping("/ai/summarize")
public ResponseEntity<String> summarizeText(@RequestBody Map<String, String> request) {
    String text = request.get("text");
    String summary = textSummarizationService.summarize(text);
    return ResponseEntity.ok(summary);
}