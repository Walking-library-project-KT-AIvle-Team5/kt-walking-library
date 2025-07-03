package miniprojectver.domain;

import lombok.*;  // ✅ 반드시 포함
import miniprojectver.infra.AbstractEvent;

@Getter @Setter @AllArgsConstructor
public class BonusPointGranted  extends AbstractEvent {
    private String userId; private Long amount;
}