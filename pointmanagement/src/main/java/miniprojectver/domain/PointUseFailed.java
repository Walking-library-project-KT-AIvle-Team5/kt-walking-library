package miniprojectver.domain;

import miniprojectver.infra.AbstractEvent;
import lombok.*;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor
public class PointUseFailed extends AbstractEvent {
    private String userId;
    private Long shortage;
    private Boolean isKtCustomer;
}