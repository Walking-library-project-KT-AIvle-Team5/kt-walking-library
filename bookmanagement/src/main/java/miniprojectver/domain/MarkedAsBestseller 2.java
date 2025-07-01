package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class MarkedAsBestseller extends AbstractEvent {

    private Long bookId;
    private Date selectedAt;

    public MarkedAsBestseller(Book aggregate) {
        super(aggregate);
    }

    public MarkedAsBestseller() {
        super();
    }
}
//>>> DDD / Domain Event
