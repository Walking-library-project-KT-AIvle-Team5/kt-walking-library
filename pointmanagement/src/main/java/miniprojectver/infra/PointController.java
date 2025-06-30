package miniprojectver.infra;

import miniprojectver.domain.*;
import miniprojectver.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/points")
@Transactional
public class PointController {

    @Autowired
    PointRepository pointRepository;

    /**
     * 1. 유저 현재 포인트 조회 (잔여 포인트만 반환)
     *    - UI 상단 잔여 포인트 등 실시간 확인용
     */
    @GetMapping("/{userId}/current")
    public Integer getCurrentPoint(@PathVariable String userId) {
        return pointRepository.findByUserId(userId)
                              .map(Point::getCurrentPoint)
                              .orElse(0);
    }

    /**
     * 2. 유저 전체 포인트 정보 조회
     *    - 디버깅, 관리자, 마이페이지 용도 등 전체 정보 필요 시
     */
    @GetMapping("/{userId}")
    public Point getPoint(@PathVariable String userId) {
        return pointRepository.findByUserId(userId).orElse(null);
    }

    /**
     * 3. 포인트 충전 요청
     *    - 실제 결제는 별도 마이크로서비스에서 처리
     *    - 이 API는 결제 성공 이후 호출됨
     */
    @PostMapping("/charge")
    public void chargePoint(@RequestBody ChargePointCommand command) {
        Point.chargePoint(command);
    }

    /**
     * 4. 포인트 사용 요청 (도서 구매, 구독권 등)
     */
    @PostMapping("/use")
    public void usePoint(@RequestBody UsePointCommand command) {
        Point.usePoint(command);
    }

    /**
     * 5. KT 고객 여부 확인 후 보너스 지급 요청
     *    - KT 고객 확인 API와 연동되거나, 관리자 시스템에서 호출 가능
     */
    @PostMapping("/bonus")
    public void grantBonusPoint(@RequestBody GrantBonusPointCommand command) {
        Point.grantBonusPoint(command);
    }
}
