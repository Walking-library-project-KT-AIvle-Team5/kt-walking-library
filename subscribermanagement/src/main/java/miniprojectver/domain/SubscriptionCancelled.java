package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionCancelled extends AbstractEvent {

    private Long subscriptionId;
    private String userId;
    private String status;
    private Date cancelledAt;
    private Date timestamp;

    public SubscriptionCancelled(SubscribeManagement aggregate) {
        super(aggregate);
    }

    public SubscriptionCancelled() {
        super();
    }
}
//>>> DDD / Domain Event
