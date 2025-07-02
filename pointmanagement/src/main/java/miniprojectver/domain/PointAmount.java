package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PointAmount extends AbstractEvent {
    private BigDecimal amount;
}
