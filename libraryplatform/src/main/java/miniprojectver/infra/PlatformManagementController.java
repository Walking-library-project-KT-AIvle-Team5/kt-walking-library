package miniprojectver.infra;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import miniprojectver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//<<< Clean Arch / Inbound Adaptor

@RestController //임시
public class PlatformManagementController {
    // 임시 구독자 데이터 (userId, status)
    private static final List<Map<String, String>> fakeSubscriberList = Arrays.asList(
        new HashMap<String, String>() {{
            put("userId", "user001");
            put("status", "ACTIVE");
        }},
        new HashMap<String, String>() {{
            put("userId", "user002");
            put("status", "CANCELLED");
        }}
    );

    @PostMapping("/test/check-subscription")
    public String testCheckSubscription(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");

        boolean isSubscribed = fakeSubscriberList.stream()
            .anyMatch(sub -> sub.get("userId").equals(userId) && sub.get("status").equals("ACTIVE"));

        SubscriptionStatusChecked event = new SubscriptionStatusChecked();
        event.setUserId(userId);
        event.setIsSubscribed(isSubscribed);
        event.publishAfterCommit();

        return "Subscription check triggered for userId=" + userId + ", isSubscribed=" + isSubscribed;
    }

    @PostMapping("/test/send-recommendation")
    public String testSendRecommendation(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");

        PlatformManagement platformManagement = new PlatformManagement();
        platformManagement.setSubscribedUserIds(userId);
        platformManagement.setRecommendationMessage("포인트가 부족합니다. 구독 요금제를 추천합니다.");

        PlatformManagement.repository().save(platformManagement);

        PaymentRecommendationMessageSent event = new PaymentRecommendationMessageSent(platformManagement);
        event.publishAfterCommit();

        return "Recommendation message triggered for userId=" + userId;
    }
}

//>>> Clean Arch / Inbound Adaptor
