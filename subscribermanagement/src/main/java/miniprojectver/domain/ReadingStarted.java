package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ReadingStarted extends AbstractEvent {

    private Long readingActivityId;
    private String userId;
    private String bookId;
    private Date startedAt;
    private Date timestamp;

    public ReadingStarted(ReadingManagement aggregate) {
        super(aggregate);
    }

    public ReadingStarted() {
        super();
    }
}
//>>> DDD / Domain Event
