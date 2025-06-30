package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionRequested extends AbstractEvent {

    private Long subscriptionId;
    private String userId;
    private Date startedAt;
    private Date endsAt;
    private Date timestamp;

    public SubscriptionRequested(SubscribeManagement aggregate) {
        super(aggregate);
    }

    public SubscriptionRequested() {
        super();
    }
}
//>>> DDD / Domain Event
