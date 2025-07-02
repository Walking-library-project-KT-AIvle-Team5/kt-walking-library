package miniprojectver.domain;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionStatusChecked extends PointAmount {

    private String userId;
    private String bookId;
    private Boolean isSubscribed;
    private BigDecimal point;

    @Override
    public BigDecimal getAmount() {
        return isSubscribed != null && isSubscribed ? BigDecimal.ZERO : point;
    }
}
