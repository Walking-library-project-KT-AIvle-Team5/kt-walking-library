package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import miniprojectver.infra.AbstractEvent;

@Data
public class SubscriptionRequested extends AbstractEvent {

    private Long subscriptionId;
    private String userId;
    private Date startedAt;
    private Date endsAt;
    private Date timestamp;
}
