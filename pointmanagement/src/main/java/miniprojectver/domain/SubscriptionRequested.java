package miniprojectver.domain;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@NoArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)   // ← 안전장치
public class SubscriptionRequested extends PointAmount {

    /** 이벤트에 들어있는 모든 필드 */
    private Long   subscriptionId;   // ← 추가
    private String userId;
    private Date   startedAt;
    private Date   endsAt;

    /** -9,900P 차감 로직 유지 */
    @Override
    public BigDecimal getAmount() {
        return BigDecimal.valueOf(-9_900);
    }
}
