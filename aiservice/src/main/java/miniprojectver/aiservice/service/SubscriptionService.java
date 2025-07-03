package miniprojectver.aiservice.service;

import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    public int calculateSubscriptionFee(String title, String author, String summary) {
        // 예시 로직: 글자수 * 10 + title hashcode 일부 + author hashcode 일부
        int base = summary.length() * 10;
        base += Math.abs(title.hashCode()) % 1000;
        base += Math.abs(author.hashCode()) % 1000;
        return base;
    }
}
