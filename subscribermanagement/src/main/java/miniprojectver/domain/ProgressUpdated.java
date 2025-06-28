package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ProgressUpdated extends AbstractEvent {

    private Long readingActivityId;
    private String userId;
    private String bookId;
    private Integer currentPage;
    private String readingStatus;
    private Date lastUpdatedAt;
    private Date timestamp;

    public ProgressUpdated(ReadingManagement aggregate) {
        super(aggregate);
    }

    public ProgressUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
