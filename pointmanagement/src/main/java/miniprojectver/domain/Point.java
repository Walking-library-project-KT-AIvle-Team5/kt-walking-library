package miniprojectver.domain;

import lombok.Data;
import miniprojectver.PointmanagementApplication;
import miniprojectver.command.*;
import javax.persistence.*;
import java.util.Optional;

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

    /* ----------------------------------------------------------
     * 1. 기본 포인트 + KT 보너스 지급 (회원가입 시)
     * ---------------------------------------------------------- */
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

        // 기본 포인트 지급
        point.setCurrentPoint(point.getCurrentPoint() + 1_000);
        point.setTotalCharged(point.getTotalCharged() + 1_000);

        // KT 고객이라면 보너스 지급
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

    /* ----------------------------------------------------------
     * 2. 포인트 충전 (결제 성공 시)
     * ---------------------------------------------------------- */
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

    /* ----------------------------------------------------------
     * 3. 포인트 사용 (책 구매 / 구독권 결제 등)
     * ---------------------------------------------------------- */
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
        deducted.publishAfterCommit();
    }
}
