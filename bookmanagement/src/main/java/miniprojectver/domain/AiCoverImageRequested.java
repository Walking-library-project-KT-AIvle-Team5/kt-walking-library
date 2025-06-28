package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AiCoverImageRequested extends AbstractEvent {

    private Long manuscriptId;
    private String title;
    private Long authorId;
    private String summary;

    public AiCoverImageRequested(Manuscript aggregate) {
        super(aggregate);
    }

    public AiCoverImageRequested() {
        super();
    }
}
//>>> DDD / Domain Event
