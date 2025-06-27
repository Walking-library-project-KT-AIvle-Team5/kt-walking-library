package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointDeducted extends AbstractEvent {

    private String userId;
    private Integer amountPoint;
    private String bookId;
    private String subscriptionId;

    public PointDeducted(Point aggregate) {
        super(aggregate);
    }

    public PointDeducted() {
        super();
    }
}
//>>> DDD / Domain Event
