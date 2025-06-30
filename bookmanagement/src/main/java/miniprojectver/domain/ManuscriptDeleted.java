package miniprojectver.domain;

import lombok.Data;
import lombok.ToString;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
public class ManuscriptDeleted extends AbstractEvent {

    private Long manuscriptId;
    private Long authorId;

    public ManuscriptDeleted(Manuscript aggregate) {
        super(aggregate);
        this.manuscriptId = aggregate.getManuscriptId();
        this.authorId = aggregate.getAuthorId();
    }

    public ManuscriptDeleted() {
        super();
    }
}
