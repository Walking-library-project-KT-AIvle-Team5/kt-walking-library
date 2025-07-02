package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PointDeducted extends AbstractEvent {
    private String userId;
    private Long amount;          // - 포인트
    private String reason;        // "SUBSCRIPTION" | "BOOK_PURCHASE"
}
