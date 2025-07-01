package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ManuscriptDeleted extends AbstractEvent {

    private Long manuscriptId;
    private Long authorId;

    public ManuscriptDeleted(Manuscript aggregate) {
        super(aggregate);
    }

    public ManuscriptDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
