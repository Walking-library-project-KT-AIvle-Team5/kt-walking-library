package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BasicPointGranted extends AbstractEvent {

    private String userId;
    private String amountPoint;

    public BasicPointGranted(Point aggregate) {
        super(aggregate);
    }

    public BasicPointGranted() {
        super();
    }
}
//>>> DDD / Domain Event
