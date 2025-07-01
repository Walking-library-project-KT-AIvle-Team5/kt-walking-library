package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class PointCharged extends AbstractEvent {

    private Long userId;
    private Integer amountPoint;

    public PointCharged(Point aggregate) {
        super(aggregate);
    }

    public PointCharged() {
        super();
    }
}
//>>> DDD / Domain Event
