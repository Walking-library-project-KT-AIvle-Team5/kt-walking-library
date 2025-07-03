package miniprojectver.infra;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import miniprojectver.domain.*;
import miniprojectver.service.SubscribeManagementService; // SubscribeManagementService 임포트

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/subscribeManagements")
//@Transactional
public class SubscribeManagementController {

    // SubscribeManagementRepository 대신 SubscribeManagementService를 주입
    @Autowired
    private SubscribeManagementService subscribeManagementService;

    // ✅ [GET] /subscribers/{userId} : 구독 여부 확인 API
    // @RequestMapping("/subscribers")가 있으므로 경로는 /{userId}가 됩니다.
    @GetMapping("/{userId}")
    public Map<String, Object> checkSubscriptionStatus(@PathVariable String userId) {
        // 서비스 레이어의 조회 메서드를 호출
        Optional<SubscribeManagement> subscriptionOpt = 
            subscribeManagementService.getSubscriptionByUserId(userId);

        Map<String, Object> result = new HashMap<>();

        if (subscriptionOpt.isPresent()) {
            SubscribeManagement sub = subscriptionOpt.get();
            result.put("userId", sub.getUserId());
            result.put("status", sub.getStatus());
            result.put("startedAt", sub.getStartedAt());
            result.put("endsAt", sub.getEndsAt());
        } else {
            result.put("userId", userId);
            result.put("status", "NOT_FOUND");
        }

        return result; 
    }

    // ✅ [POST] /subscribers : 새로운 구독 생성 API
    // @RequestMapping("/subscribers")가 있으므로 경로는 비어있거나 "/"가 됩니다.
    @PostMapping
    public SubscribeManagement createSubscription(@RequestBody Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        if (userId == null || userId.trim().isEmpty()) {
            // 적절한 HTTP 상태 코드를 포함하는 예외를 던지는 것이 더 좋습니다 (예: @ResponseStatus(HttpStatus.BAD_REQUEST))
            throw new IllegalArgumentException("User ID is required for subscription.");
        }
        // 서비스 레이어의 비즈니스 로직 호출
        return subscribeManagementService.requestNewSubscription(userId);
    }
}
//>>> Clean Arch / Inbound Adaptor
