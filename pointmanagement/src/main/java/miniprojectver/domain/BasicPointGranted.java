// event/BasicPointGranted.java
package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BasicPointGranted extends AbstractEvent {
    private String userId;
    private Long amount;   // 항상 1000L
}

