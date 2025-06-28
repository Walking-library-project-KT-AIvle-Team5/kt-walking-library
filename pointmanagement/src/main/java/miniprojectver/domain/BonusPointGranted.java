package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BonusPointGranted extends AbstractEvent {

    private Long userId;
    private Integer amountPoint;

    public BonusPointGranted(Point aggregate) {
        super(aggregate);
    }

    public BonusPointGranted() {
        super();
    }
}
//>>> DDD / Domain Event
