package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointChargeFailed extends AbstractEvent {

    private String userId;

    public PointChargeFailed(Point aggregate) {
        super(aggregate);
    }

    public PointChargeFailed() {
        super();
    }
}
//>>> DDD / Domain Event
