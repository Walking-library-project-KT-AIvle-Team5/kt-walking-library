package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
public class BookUnpublished extends AbstractEvent {

    private Long id;
    private String title;
    private BookStatus status;
    private String summaryContent;
    private String summary;
    private String category;
    private String imagepath;
    private String contents;
    private Integer price;

    public BookUnpublished(BookPublication aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.title = aggregate.getTitle();
        this.status = aggregate.getStatus();
        this.summaryContent = aggregate.getSummaryContent();
        this.summary = aggregate.getSummary();
        this.category = aggregate.getCategory();
        this.imagepath = aggregate.getImagepath();
        this.contents = aggregate.getContents();
        this.price = aggregate.getPrice();
    }

    public BookUnpublished() {
        super();
    }
}
