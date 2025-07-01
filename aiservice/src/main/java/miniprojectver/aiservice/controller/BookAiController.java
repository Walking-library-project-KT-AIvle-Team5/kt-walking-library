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
