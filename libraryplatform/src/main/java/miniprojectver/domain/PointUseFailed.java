package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class PointUseFailed extends AbstractEvent {

    private String userId;
    private Boolean isktCustomer;
    private Integer currentPoint;
    private Integer neededPoint;

    public PointUseFailed(Object aggregate) {
        super(aggregate); // 그냥 Object로 넘겨도 AbstractEvent 내부에서 ID만 쓰면 OK
    }

    public PointUseFailed() {
        super();
    }
}

//>>> DDD / Domain Event
