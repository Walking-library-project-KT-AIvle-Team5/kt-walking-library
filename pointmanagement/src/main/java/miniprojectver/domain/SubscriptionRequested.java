package miniprojectver.domain;

import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
public class SubscriptionRequested extends AbstractEvent {

    private Long subscriptionId;
    private String userId;
    private Date startedAt;
    private Date endsAt;
    private Date timestamp;
}
