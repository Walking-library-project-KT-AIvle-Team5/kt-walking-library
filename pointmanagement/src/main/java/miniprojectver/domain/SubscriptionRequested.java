package miniprojectver.domain;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class SubscriptionRequested extends PointAmount {

    private String userId;
    private Date startedAt;
    private Date endsAt;

    @Override
    public BigDecimal getAmount() {
        return BigDecimal.valueOf(-9_900);
    }
}
