package miniprojectver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointPurchaseRequested extends PointAmount {

    private Long pointRequestId;
    private String userId;
    private String paymentMethodId;
    private BigDecimal actualPaymentAmount;

    @Override                // 🔥 추가
    public BigDecimal getAmount() {
        return actualPaymentAmount;   // JSON에 항상 존재
    }
}