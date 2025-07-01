package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
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
    private Date createAt;
    private Boolean isBestseller;
    private status status;
    private Integer price;

    public BookRegistered(Book aggregate) {
        super(aggregate);
    }

    public BookRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
