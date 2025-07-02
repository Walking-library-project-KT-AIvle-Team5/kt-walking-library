package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BestsellerRegistered extends AbstractEvent {
    private Long bookId;
    
}
