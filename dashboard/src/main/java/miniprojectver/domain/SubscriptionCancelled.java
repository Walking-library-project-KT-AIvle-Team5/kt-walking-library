package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import miniprojectver.infra.AbstractEvent;

@Data
public class SubscriptionCancelled extends AbstractEvent {

    private Long subscriptionId;
    private String userId;
    private String status;
    private Date cancelledAt;
    private Date timestamp;
}
