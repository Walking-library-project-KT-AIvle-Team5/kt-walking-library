package miniprojectver.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import miniprojectver.PointmanagementApplication;
import miniprojectver.message.*;          // Kafka 메시지 수신용 DTO (JPA 아님)
import javax.persistence.*;
import java.util.Date;
import java.util.Optional;
import miniprojectver.domain.*; //// 이벤트 관련 도메인 클래스 (이벤트 발생시 사용)

@Entity
@Table(name = "Point_table")
@NoArgsConstructor
@Data
public class Point {

    /* ---------- ① 필드 ---------- */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long   pointAccountId;

    private String  userId;
    private Integer currentPoint;   // 잔여 포인트
    private Long    totalCharged;   // 누적 충전
    private Long    totalUsed;      // 누적 사용
    private Boolean isktCustomer;   // KT 통신사 여부

    /* ---------- ② 편의 ─ Repository 핸드오프 ---------- */
    public static PointRepository repository() {
        return PointmanagementApplication.applicationContext
                                         .getBean(PointRepository.class);
    }

    /* ============================================================
       📌  이벤트 기반 Port 메서드
       ============================================================ */

    /* 1. 회원 가입  →  기본 + (조건)보너스 지급 */
    public static void grantPointOnMemberJoined(MemberJoined evt) {

        Point point = repository().findByUserId(evt.getUserId())
                                  .orElseGet(Point::new);

        if (point.getPointAccountId() == null) {          // 최초 계정
            point.setUserId(evt.getUserId());
            point.setCurrentPoint(0);
            point.setTotalCharged(0L);
            point.setTotalUsed(0L);
            point.setIsktCustomer(evt.getIsktCustomer());
        }

        // ▸ 기본 1 000 p
        point.addPoint(1_000);
        BasicPointGranted basicEvt = new BasicPointGranted(point, 1_000);
        basicEvt.publishAfterCommit();

        // ▸ KT 고객이면 5 000 p 추가
        if (Boolean.TRUE.equals(evt.getIsktCustomer())) {
            point.addPoint(5_000);
            BonusPointGranted bonusEvt = new BonusPointGranted(point, 5_000);
            bonusEvt.publishAfterCommit();
        }

        repository().save(point);
    }

    /* 2. 포인트 충전 요청  →  바로 충전(결제는 별도 시스템) */
    public static void chargePoint(PointPurchaseRequested evt) {
        if (evt.getAmount() == null || evt.getAmount() <= 0) return;

        Point point = repository().findByUserId(evt.getUserId())
                                  .orElseGet(() -> Point.empty(evt.getUserId()));

        point.addPoint(evt.getAmount());
        repository().save(point);
        System.out.println("✅ 포인트 저장 완료: " + point);

        PointCharged chargedEvt = new PointCharged(point, evt.getAmount());
        chargedEvt.publishAfterCommit();
    }

    /* 3-A. 구독 신청 →  9 900 p 차감 시도 */
    public static void deductForSubscription(SubscriptionRequested evt) {
        tryDeduct(evt.getUserId(), 9_900, null, null);
    }

    /* 3-B. 책 구매 요청 →  금액만큼 차감 시도 */
    public static void deductForBook(BookPurchaseRequested evt) {
        tryDeduct(evt.getUserId(), evt.getAmount(), evt.getBookId(), evt.getAuthorId());
    }

    /* ---------- ③ 공통 차감 로직 ---------- */
    private static void tryDeduct(String userId,
                                  int amount,
                                  String bookId,
                                  String authorId) {

        Point point = repository().findByUserId(userId).orElse(null);
        if (point == null || point.getCurrentPoint() < amount) {
            /* 잔액 부족 → 실패 이벤트 */
            PointUseFailed fail = new PointUseFailed(
                    userId,
                    amount,
                    point == null ? 0 : point.getCurrentPoint(),
                    point != null && Boolean.TRUE.equals(point.getIsktCustomer()));
            fail.publishAfterCommit();
            return;
        }

        /* 정상 차감 */
        point.setCurrentPoint(point.getCurrentPoint() - amount);
        point.setTotalUsed(point.getTotalUsed() + amount);
        repository().save(point);

        PointDeducted deducted = new PointDeducted(point, amount, bookId, authorId, new Date());
        deducted.publishAfterCommit();
    }

    /* ---------- ④ 유틸 ---------- */
    private void addPoint(int amt) {
        this.currentPoint = (currentPoint == null ? 0 : currentPoint) + amt;
        this.totalCharged = (totalCharged == null ? 0L : totalCharged) + amt;
    }

    private static Point empty(String userId) {
        Point p = new Point();
        p.setUserId(userId);
        p.setCurrentPoint(0);
        p.setTotalCharged(0L);
        p.setTotalUsed(0L);
        p.setIsktCustomer(false);
        return p;
    }
}
