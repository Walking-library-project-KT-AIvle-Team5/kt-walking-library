package miniprojectver.infra;

import lombok.RequiredArgsConstructor;
import miniprojectver.domain.PointRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;              // ← log 사용 위해
import miniprojectver.domain.Point;            // ← Point 엔티티 import

@Slf4j 
@RestController
@RequiredArgsConstructor
public class PointQueryController {

    private final PointRepository pointRepo;

    /** GET /points/{userId} → 현재 잔액(Long) */
    @GetMapping("/points/{userId}")
    public ResponseEntity<Long> balance(@PathVariable String userId){
        return pointRepo.findByUserId(userId)
                .map(p -> ResponseEntity.ok(p.getCurrentAmount()))
                .orElse(ResponseEntity.notFound().build());
    }

     /* ───────── 테스트용 충전 엔드포인트 ───────── */
    @PostMapping("/points/charge")
    @Transactional      // ★ DB 반영
    public ResponseEntity<String> charge(@RequestParam String  userId,
                                         @RequestParam boolean isKtCustomer,
                                         @RequestParam Long    amount){

        // ① 계좌 조회 or 생성
        Point p = pointRepo.findByUserId(userId)
                           .orElse(Point.create(userId, isKtCustomer));

        // ② 기본 1 000 P + (KT면 +5 000 P) + 입력한 amount
        long diff = 1_000L + (isKtCustomer ? 5_000L : 0L) + amount;
        p.apply(diff);
        pointRepo.save(p);

        log.info("★ 테스트 충전 완료 userId={}, +{}P, 잔액={}", userId, diff, p.getCurrentAmount());
        return ResponseEntity.ok("충전 성공, 현재 잔액 = " + p.getCurrentAmount());
    }
}

