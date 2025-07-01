package miniprojectver.infra;

import java.util.HashMap;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import miniprojectver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/subscribeManagements")
@Transactional
public class SubscribeManagementController {

    @Autowired
    SubscribeManagementRepository subscribeManagementRepository;
    
    
    // ✅ [GET] /subscribers/{userId} : 구독 여부 확인 API
    @GetMapping("/{userId}")
    public Map<String, Object> checkSubscriptionStatus(@PathVariable String userId) {
        Optional<SubscribeManagement> subscriptionOpt =
            subscribeManagementRepository.findByUserId(userId);

        Map<String, Object> result = new HashMap<>();

        if (subscriptionOpt.isPresent()) {
            SubscribeManagement sub = subscriptionOpt.get();
            result.put("userId", sub.getUserId());
            result.put("status", sub.getStatus()); // ACTIVE, CANCELLED 등
            result.put("startedAt", sub.getStartedAt());
            result.put("endsAt", sub.getEndsAt());
        } else {
            result.put("userId", userId);
            result.put("status", "NOT_FOUND");
        }

        return result;
    }
}
//>>> Clean Arch / Inbound Adaptor
