package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import miniprojectver.infra.AbstractEvent;

@Data
public class SubscriptionRequested extends AbstractEvent {

    private Long subscriptionId;
    private String userId;
    private Long startedAt;
    private Long endsAt;
    private Long timestamp;
}
