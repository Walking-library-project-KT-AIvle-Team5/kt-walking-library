package miniprojectver.infra;

import lombok.RequiredArgsConstructor;
import miniprojectver.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {

    private final PointRepository repo;
    private final BestsellerTrackerRepository trackerRepo;

    /* ▶ 현재 포인트 조회 */
    @GetMapping("/{userId}")
    public Long getCurrentPoint(@PathVariable String userId) {
        return repo.findByUserId(userId)
                   .map(Point::getCurrentPoint)
                   .orElse(0L);
    }

    /* ▶ 회원가입: 기본 + 보너스 지급 */
    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody Map<String, Object> req) {
        String  userId = req.get("userId").toString();
        boolean isKt   = Boolean.parseBoolean(req.get("isktCustomer").toString());

        Point point = Point.create(userId, isKt);
        repo.save(point);

        return Map.of("userId", userId, "currentPoint", point.getCurrentPoint());
    }

    /* ▶ 포인트 충전 */
    @PostMapping("/charge")
    public Map<String, Object> charge(@RequestBody Map<String, Object> req) {
        String userId = req.get("userId").toString();
        long   amount = Long.parseLong(req.get("amount").toString());

        Point point = repo.findByUserId(userId).orElseThrow();
        point.charge(amount);
        repo.save(point);

        return Map.of("userId", userId, "currentPoint", point.getCurrentPoint());
    }

    /* ▶ 도서 구매 */
    @PostMapping("/use")
    public Map<String, Object> use(@RequestBody Map<String, Object> req) {
        String  userId       = req.get("userId").toString();
        Long    bookId       = req.get("bookId") != null ? Long.valueOf(req.get("bookId").toString()) : null;
        boolean isSubscribed = Boolean.parseBoolean(req.get("isSubscribed").toString());
        Long    price        = Long.valueOf(req.get("price").toString());

        long usedAmount = isSubscribed ? 0L : price;

        Point point   = repo.findByUserId(userId).orElseThrow();
        boolean ok    = point.use(usedAmount);
        repo.save(point);

        if (ok) {
            /* 누적 구매 카운터 */
            if (bookId != null && usedAmount > 0) {
                BestsellerTracker tracker = trackerRepo.findById(bookId)
                        .orElse(BestsellerTracker.builder()
                                .bookId(bookId)
                                .purchaseCount(0L)
                                .bestseller(false)
                                .build());
                tracker.increment();
                trackerRepo.save(tracker);
            }
            return Map.of("userId", userId,
                          "used", true,
                          "usedAmount", usedAmount,
                          "currentPoint", point.getCurrentPoint());
        } else {
            return Map.of("userId", userId,
                          "used", false,
                          "message", "포인트 부족");
        }
    }
}
