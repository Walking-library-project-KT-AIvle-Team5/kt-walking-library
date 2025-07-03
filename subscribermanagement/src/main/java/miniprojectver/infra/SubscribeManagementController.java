package miniprojectver.infra;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // HttpStatus 임포트 추가
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException; // ResponseStatusException 임포트 추가

import miniprojectver.domain.*;
import miniprojectver.service.SubscribeManagementService;

@RestController
@RequestMapping(value="/subscribeManagements")
@Transactional
public class SubscribeManagementController {

    @Autowired
    private SubscribeManagementService subscribeManagementService;

    // ✅ [GET] /subscribeManagements/{userId} : 구독 여부 확인 API
    @GetMapping("/{userId}")
    public Map<String, Object> checkSubscriptionStatus(@PathVariable String userId) {
        Optional<SubscribeManagement> subscriptionOpt = 
            subscribeManagementService.getSubscriptionByUserId(userId);

        Map<String, Object> result = new HashMap<>();

        if (subscriptionOpt.isPresent()) {
            SubscribeManagement sub = subscriptionOpt.get();
            result.put("subscriptionId", sub.getSubscriptionId()); // subscriptionId도 포함하는 것이 좋습니다.
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

    // ✅ [POST] /subscribeManagements : 새로운 구독 생성 API
    @PostMapping
    public SubscribeManagement createSubscription(@RequestBody Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        if (userId == null || userId.trim().isEmpty()) {
            // 더 적절한 예외 처리: ResponseStatusException 사용
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is required for subscription.");
        }
        return subscribeManagementService.requestNewSubscription(userId);
    }

    // ✅ [DELETE] /subscribeManagements/{subscriptionId} : 구독 취소 API (새로 추가할 부분)
    @DeleteMapping("/{subscriptionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 삭제 성공 시 204 No Content 반환
    public void cancelSubscription(@PathVariable Long subscriptionId) {
        try {
            subscribeManagementService.cancelExistingSubscription(subscriptionId);
        } catch (IllegalArgumentException e) {
            // 구독 ID를 찾을 수 없는 경우 404 Not Found 반환
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // ✅ [PUT] /subscribeManagements/{subscriptionId}/activate : 구독 활성화 API (선택 사항, 서비스에 activateSubscription 있으므로)
    // 활성화는 '변경'의 의미가 강하므로 PUT 또는 PATCH를 사용할 수 있습니다.
    // 여기서는 특정 상태로 '전이'시키는 명령으로 보고 PUT을 사용합니다.
    @PutMapping("/{subscriptionId}/activate")
    public SubscribeManagement activateSubscription(@PathVariable Long subscriptionId) {
        try {
            return subscribeManagementService.activateSubscription(subscriptionId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
