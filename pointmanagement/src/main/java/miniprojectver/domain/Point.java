package miniprojectver.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pointAccountId;

    private String userId;  // ✅ Long → String
    private Long currentPoint;
    private Long totalCharged;
    private Long totalUsed;
    private Boolean isktCustomer;

    public static Point create(String userId, boolean isKtCustomer) {  // ✅ Long → String
        long basic = 1_000L + (isKtCustomer ? 5_000L : 0L);
        return Point.builder()
                .userId(userId)
                .currentPoint(basic)
                .totalCharged(basic)
                .totalUsed(0L)
                .isktCustomer(isKtCustomer)
                .build();
    }

    public void charge(long amount) {
        this.currentPoint += amount;
        this.totalCharged += amount;
    }

    public boolean use(long amount) {
        if (this.currentPoint < amount) return false;
        this.currentPoint -= amount;
        this.totalUsed += amount;
        return true;
    }
}
