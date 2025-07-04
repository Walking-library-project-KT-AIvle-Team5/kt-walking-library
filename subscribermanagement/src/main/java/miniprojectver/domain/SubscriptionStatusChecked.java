package miniprojectver.domain;

import lombok.Data;
import lombok.ToString;
import miniprojectver.infra.AbstractEvent;
import java.math.BigDecimal;

@Data
@ToString
public class SubscriptionStatusChecked extends AbstractEvent {
    private String userId;
    private String bookId;
    private BigDecimal price;
    private Boolean isSubscribed;

    public SubscriptionStatusChecked() {
        super();
    }
}
