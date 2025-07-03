package miniprojectver.domain;

import java.util.Date;
import lombok.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BookRegistered extends AbstractEvent {

    private Long bookId;
    private String bookName;
    private Long authorId;
    private String authorName;
    private String category;
    private String summary;
    private String imagePath;
    private String contents;
    private Date publishedAt;
    private Boolean isBestseller;
    private BookStatus status;
    private Integer price;

    public BookRegistered(Book aggregate) {
        super(aggregate);
        this.bookId = aggregate.getBookId();
        this.bookName = aggregate.getBookName();
        this.authorId = aggregate.getAuthorId();
        this.authorName = aggregate.getAuthorName();
        this.category = aggregate.getCategory();
        this.summary = aggregate.getSummary();
        this.imagePath = aggregate.getImagePath();
        this.contents = aggregate.getContents();
        this.publishedAt = aggregate.getPublishedAt();
        this.isBestseller = aggregate.getIsBestseller();
        this.status = aggregate.getStatus();
        this.price = aggregate.getPrice();
    }

    public BookRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
