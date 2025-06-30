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
public class PointUseFailed extends AbstractEvent {

    private String userId;
    private Boolean isktCustomer;
    private Integer currentPoint;
    private Integer neededPoint;

    public PointUseFailed(String userId, int neededPoint, int currentPoint, boolean isktCustomer) {
    super();
    this.userId = userId;
    this.neededPoint = neededPoint;
    this.currentPoint = currentPoint;
    this.isktCustomer = isktCustomer;
}


    public PointUseFailed() {
        super();
    }
}
//>>> DDD / Domain Event
