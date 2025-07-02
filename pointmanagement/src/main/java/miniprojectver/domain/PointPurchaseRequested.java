package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PointPurchaseRequested extends AbstractEvent {
    private String userId;
    private Long amount;
}
