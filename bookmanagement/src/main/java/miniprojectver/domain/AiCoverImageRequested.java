package miniprojectver.domain;

import lombok.Data;
import lombok.ToString;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
public class AiCoverImageRequested extends AbstractEvent {

    private Long manuscriptId;
    private String title;
    private Long authorId;
    private String summary;

    public AiCoverImageRequested(Manuscript aggregate) {
        super(aggregate);
        this.manuscriptId = aggregate.getManuscriptId();
        this.title = aggregate.getTitle();
        this.authorId = aggregate.getAuthorId();
        this.summary = aggregate.getSummary();
    }

    public AiCoverImageRequested() {
        super();
    }
}
