package miniprojectver.aiservice.controller;

import miniprojectver.aiservice.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscription/calculate")
    public ResponseEntity<Integer> calculateSubscription(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        String author = request.get("author");
        String summary = request.get("summary");
        int fee = subscriptionService.calculateSubscriptionFee(title, author, summary);
        return ResponseEntity.ok(fee);
    }
}
