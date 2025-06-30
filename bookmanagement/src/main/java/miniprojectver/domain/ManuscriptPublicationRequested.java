package miniprojectver.domain;

import miniprojectver.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ManuscriptPublicationRequested extends AbstractEvent {

    private Long manuscriptId;
    private String title;
    private Long authorId;
    private String content;
    private ManuscriptStatus status;
    private String category;
    private String imagePath;
    private String summary;
    private Integer price;

    public ManuscriptPublicationRequested(Manuscript aggregate){
        super(aggregate);
        this.manuscriptId = aggregate.getManuscriptId();
        this.title = aggregate.getTitle();
        this.authorId = aggregate.getAuthorId();
        this.content = aggregate.getContent();
        this.status = aggregate.getStatus();
        this.category = aggregate.getCategory();
        this.imagePath = aggregate.getImagePath();
        this.summary = aggregate.getSummary();
        this.price = aggregate.getPrice();
    }

    public ManuscriptPublicationRequested(){
        super();
    }
}
