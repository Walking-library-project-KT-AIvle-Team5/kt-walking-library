package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SubscriptionStatusChecked extends AbstractEvent {
    private String userId;
    private Long bookId;
    private Boolean isSubscribed;
    private Long price;
}
