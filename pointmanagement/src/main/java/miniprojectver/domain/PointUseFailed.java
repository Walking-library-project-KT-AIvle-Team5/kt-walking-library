package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointUseFailed extends AbstractEvent {

    private String userId;
    private Boolean isktCustomer;
    private Integer currentPoint;
    private Integer neededPoint;

    public PointUseFailed(Point aggregate) {
        super(aggregate);
    }

    public PointUseFailed() {
        super();
    }
}
//>>> DDD / Domain Event
