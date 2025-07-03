// subscribermanagement/src/main/java/miniprojectver/domain/PointDeducted.java
// (또는 miniprojectver.event.external 등 적절한 패키지)

package miniprojectver.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor; // @AllArgsConstructor는 외부에서 받을 때는 보통 필요없으나, Lombok 편의상 유지

import java.math.BigDecimal; // 금액 타입을 BigDecimal로 변경 고려 (정확한 계산을 위해)

@Getter
@Setter
@AllArgsConstructor // Lombok 편의상 유지
@NoArgsConstructor // 역직렬화를 위해 필수
public class PointDeducted {
    private String userId;
    private Long amount; // - 포인트 (Long으로 유지하거나 BigDecimal로 변경 고려)
    private String reason; // "SUBSCRIPTION" | "BOOK_PURCHASE"

    // // **** 중요: pointmanagement 서비스의 PointDeducted에 이 필드를 추가했다면 여기에 포함하세요. ****
    // private Long relatedSubscriptionId; // 구독 관련 이벤트라면 해당 subscriptionId
}
