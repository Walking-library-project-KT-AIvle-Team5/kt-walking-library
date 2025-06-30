package miniprojectver.domain;

import miniprojectver.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ManuscriptEdited extends AbstractEvent {

    private Long manuscriptId;
    private String title;
    private Long authorId;
    private String content;
    private ManuscriptStatus status;

    public ManuscriptEdited(Manuscript aggregate){
        super(aggregate);
        this.manuscriptId = aggregate.getManuscriptId();
        this.title = aggregate.getTitle();
        this.authorId = aggregate.getAuthorId();
        this.content = aggregate.getContent();
        this.status = aggregate.getStatus();
    }

    public ManuscriptEdited(){
        super();
    }
}

//>>> DDD / Domain Event