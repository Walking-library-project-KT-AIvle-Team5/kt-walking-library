package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ReadingCompleted extends AbstractEvent {

    private Long readingActivityId;
    private String userId;
    private String bookId;
    private Integer currentPage;
    private String readingStatus;
    private Date completedAt;
    private Date timestamp;

    public ReadingCompleted(ReadingManagement aggregate) {
        super(aggregate);
    }

    public ReadingCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
