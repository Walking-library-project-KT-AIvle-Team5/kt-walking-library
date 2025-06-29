package miniprojectver.domain;

import java.util.Date;
import lombok.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class PointDeducted extends AbstractEvent {

    private String userId;         // 포인트를 사용한 사용자
    private Integer amount;        // 차감된 포인트
    private String bookId;         // 구매한 책 ID
    private String authorId;       // 🔹 추가: 저자 ID
    private Date purchasedAt;      // 🔹 추가: 구매 일시
    private String subscriptionId; // 구독권 구매시 식별자 (필요 시 사용)

    public PointDeducted(Point aggregate) {
        super(aggregate);
    }

    public PointDeducted() {
        super();
    }
}
//>>> DDD / Domain Event
