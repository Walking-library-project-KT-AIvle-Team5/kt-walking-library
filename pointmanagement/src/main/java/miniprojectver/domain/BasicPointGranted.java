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
public class BasicPointGranted extends AbstractEvent {

    private String userId;
    private Integer amount;

    public BasicPointGranted(Point aggregate, Integer amount) {
    super(aggregate);
    this.userId = aggregate.getUserId();
    this.amount = amount;
}

    public BasicPointGranted() {
        super();
    }
}
//>>> DDD / Domain Event
