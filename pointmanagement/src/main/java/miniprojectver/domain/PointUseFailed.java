package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PointUseFailed extends AbstractEvent {
    private String userId;
    private Long amount;
    private Long bookId;
}
