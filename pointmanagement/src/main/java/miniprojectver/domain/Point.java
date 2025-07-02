package miniprojectver.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Point {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointAccountId;

    @Column(nullable = false, unique = true)
    private String userId;          // 식별자

    private Long currentAmount = 0L;
    private Long totalCharged  = 0L;
    private Long totalUsed     = 0L;

    private Boolean isKtCustomer;

    /* ──────────────────────────────── */

    /** 신규 계좌 팩토리 */
    public static Point create(String userId, boolean isKtCustomer){
        Point p = new Point();
        p.userId = userId;
        p.isKtCustomer = isKtCustomer;
        return p;
    }

    /** 충전(+) & 사용(-) 공용 함수 */
    public void apply(Long diff){
        if(diff == null) return;
        long after = this.currentAmount + diff;
        if(after < 0) throw new IllegalStateException("잔액 부족");
        this.currentAmount = after;

        if(diff > 0)  this.totalCharged += diff;
        if(diff < 0)  this.totalUsed    += Math.abs(diff);
    }
}
