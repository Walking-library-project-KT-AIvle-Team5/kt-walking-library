package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MemberJoined extends AbstractEvent {
    private String userId;
    private Boolean isktCustomer;
}
