package miniprojectver.domain;

import lombok.Data;
import lombok.ToString;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
public class EbookAnalysisRequested extends AbstractEvent {

    private Long manuscriptId;
    private String title;
    private Long authorId;
    private String content;

    public EbookAnalysisRequested(Manuscript aggregate) {
        super(aggregate);
        this.manuscriptId = aggregate.getManuscriptId();
        this.title = aggregate.getTitle();
        this.authorId = aggregate.getAuthorId();
        this.content = aggregate.getContent();
    }

    public EbookAnalysisRequested() {
        super();
    }
}

//>>> DDD / Domain Event
