package miniprojectver.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionStatusChecked extends AbstractEvent {

    private Long id;
    private String userId;
    private Boolean isSubscribed;
    private String bookId;
    private BigDecimal price;
    private Long purchaseRequestId;
    public SubscriptionStatusChecked(PlatformManagement aggregate) {
        super(aggregate);
    }

    public SubscriptionStatusChecked() {
        super();
    }
}
//>>> DDD / Domain Event
