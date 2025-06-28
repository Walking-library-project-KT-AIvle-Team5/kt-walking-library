package miniprojectver.domain;

import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
public class SubscriptionStatusChecked extends AbstractEvent {

    private Long id;
    private String userId;
    private Boolean isSubscribed;
}
