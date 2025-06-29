package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class BonusPointGranted extends AbstractEvent {

    private String userId;
    private Integer amount;

    public BonusPointGranted(Point aggregate) {
        super(aggregate);
    }

    public BonusPointGranted() {
        super();
    }
}
//>>> DDD / Domain Event
