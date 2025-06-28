package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class EbookAnalysisRequested extends AbstractEvent {

    private Long manuscriptId;
    private String title;
    private Long authorId;
    private String content;

    public EbookAnalysisRequested(Manuscript aggregate) {
        super(aggregate);
    }

    public EbookAnalysisRequested() {
        super();
    }
}
//>>> DDD / Domain Event
