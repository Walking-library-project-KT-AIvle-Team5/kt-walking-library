package miniprojectver.infra;

import lombok.RequiredArgsConstructor;
import miniprojectver.domain.*;
import miniprojectver.message.*;
import org.springframework.transaction.annotation.Transactional;  // ✅ 추가 필요
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {

    private final PointRepository repo;

    /* 현재 잔여 포인트 조회 */
    @GetMapping("/{userId}")
    public Integer currentPoint(@PathVariable String userId) {
        return repo.findByUserId(userId)
                   .map(Point::getCurrentPoint)
                   .orElse(0);
    }

    /* 포인트 충전 */
    @Transactional
    @PostMapping("/charge")
    public void chargePoint(@RequestBody PointPurchaseRequested command) {
        Point.chargePoint(command);
    }

    /* 구독권 포인트 차감 */
    @Transactional
    @PostMapping("/deduct/subscription")
    public void deductForSubscription(@RequestBody SubscriptionRequested command) {
        Point.deductForSubscription(command);
    }

    /* 도서 구매 포인트 차감 */
    @Transactional
    @PostMapping("/deduct/book")
    public void deductForBook(@RequestBody BookPurchaseRequested command) {
        Point.deductForBook(command);
    }
}
