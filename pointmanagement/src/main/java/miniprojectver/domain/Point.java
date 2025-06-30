package miniprojectver.domain;

import lombok.Data;
import miniprojectver.PointmanagementApplication;
import miniprojectver.command.*;
import miniprojectver.domain.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Point_table")
@Data
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pointAccountId;

    private String userId;
    private Integer currentPoint;
    private Long totalCharged;
    private Long totalUsed;
    private Boolean isktCustomer;

    public static PointRepository repository() {
        return PointmanagementApplication.applicationContext.getBean(PointRepository.class);
    }

    // ✅ 1. 기본 포인트 + KT 보너스 지급
    public static void grantBasicPoint(MemberJoined memberJoined) {
        Point point = repository()
            .findByUserId(memberJoined.getUserId())
            .orElseGet(Point::new);

        if (point.getPointAccountId() == null) {
            point.setUserId(memberJoined.getUserId());
            point.setTotalCharged(0L);
            point.setTotalUsed(0L);
            point.setCurrentPoint(0);
            point.setIsktCustomer(memberJoined.getIsKtCustomer());
        }

        point.setCurrentPoint(point.getCurrentPoint() + 1_000);
        point.setTotalCharged(point.getTotalCharged() + 1_000);

        if (Boolean.TRUE.equals(memberJoined.getIsKtCustomer())) {
            point.setCurrentPoint(point.getCurrentPoint() + 5_000);
            point.setTotalCharged(point.getTotalCharged() + 5_000);

            BonusPointGranted bonusEvt = new BonusPointGranted(point);
            bonusEvt.setAmount(5_000);
            bonusEvt.publishAfterCommit();
        }

        repository().save(point);

        BasicPointGranted basicEvt = new BasicPointGranted(point);
        basicEvt.setAmount(1_000);
        basicEvt.publishAfterCommit();
    }

    // ✅ 2. 포인트 충전
    public static void chargePoint(ChargePointCommand cmd) {
        if (cmd.getAmount() == null || cmd.getAmount() <= 0) {
            PointChargeFailed failed = new PointChargeFailed();
            failed.setUserId(cmd.getUserId());
            failed.setAmount(cmd.getAmount());
            failed.publishAfterCommit();
            return;
        }

        Point point = repository()
            .findByUserId(cmd.getUserId())
            .orElseGet(() -> {
                Point newPoint = new Point();
                newPoint.setUserId(cmd.getUserId());
                newPoint.setCurrentPoint(0);
                newPoint.setTotalCharged(0L);
                newPoint.setTotalUsed(0L);
                newPoint.setIsktCustomer(false);
                return newPoint;
            });

        point.setCurrentPoint(point.getCurrentPoint() + cmd.getAmount());
        point.setTotalCharged(point.getTotalCharged() + cmd.getAmount());
        repository().save(point);

        PointCharged evt = new PointCharged(point);
        evt.setAmount(cmd.getAmount());
        evt.publishAfterCommit();
    }

    // ✅ 3. 포인트 사용
    public static void usePoint(UsePointCommand cmd) {
        Point point = repository().findByUserId(cmd.getUserId()).orElse(null);

        if (point == null) {
            PointUseFailed failed = new PointUseFailed();
            failed.setUserId(cmd.getUserId());
            failed.setNeededPoint(cmd.getAmount());
            failed.setCurrentPoint(0);
            failed.setIsktCustomer(false);
            failed.publishAfterCommit();
            return;
        }

        if (point.getCurrentPoint() < cmd.getAmount()) {
            PointUseFailed failed = new PointUseFailed(point);
            failed.setNeededPoint(cmd.getAmount());
            failed.setCurrentPoint(point.getCurrentPoint());
            failed.setIsktCustomer(point.getIsktCustomer());
            failed.publishAfterCommit();
            return;
        }

        point.setCurrentPoint(point.getCurrentPoint() - cmd.getAmount());
        point.setTotalUsed(point.getTotalUsed() + cmd.getAmount());
        repository().save(point);

        PointDeducted deducted = new PointDeducted(point);
        deducted.setAmount(cmd.getAmount());
        deducted.setBookId(cmd.getBookId());
        deducted.setAuthorId(cmd.getAuthorId());
        deducted.setPurchasedAt(new Date());
        deducted.publishAfterCommit();
    }

    // ✅ 4. 구독 시 포인트 차감 시도 - 두 경우에 대응
    public static void tryPointDeduction(SubscriptionRequested event) {
        System.out.println("🔁 [구독 요청에 대한 포인트 차감 시도] " + event);
        // 실제 차감 로직을 넣거나, UsePointCommand 로 바꿔서 발행 가능
    }

    public static void tryPointDeduction(SubscriptionStatusChecked event) {
        System.out.println("🔁 [구독 상태 확인에 따른 포인트 차감 시도] " + event);
        // 마찬가지로 실제 차감 UsePointCommand 발행 로직 구현 가능
    }

    // ✅ 5. 추가 보너스 포인트 지급 커맨드 대응
    public static void grantBonusPoint(GrantBonusPointCommand cmd) {
        Point point = repository().findByUserId(cmd.getUserId()).orElse(null);
        if (point == null) return;

        point.setCurrentPoint(point.getCurrentPoint() + cmd.getAmount());
        point.setTotalCharged(point.getTotalCharged() + cmd.getAmount());
        repository().save(point);

        BonusPointGranted evt = new BonusPointGranted(point);
        evt.setAmount(cmd.getAmount());
        evt.publishAfterCommit();
    }
}
