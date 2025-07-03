package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@ToString
public class BookPublicationChecked extends AbstractEvent {

    private Long id;
    private String title;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BookStatus status;
    private String summaryContent;
    private String summary;
    private String category;
    private String imagepath;
    private String contents;
    private Integer price;

    public BookPublicationChecked(BookPublication aggregate) {
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

    public BookPublicationChecked() {
        super();
    }
}
