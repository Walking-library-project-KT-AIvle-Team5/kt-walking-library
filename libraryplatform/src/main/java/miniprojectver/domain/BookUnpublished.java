package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BookUnpublished extends AbstractEvent {

    private Long id;
    private String title;
    private String status;
    private String summaryContent;
    private String summary;
    private String category;
    private String imagepath;
    private String contents;
    private Integer price;

    public BookUnpublished(BookPublication aggregate) {
        super(aggregate);
    }

    public BookUnpublished() {
        super();
    }
}
//>>> DDD / Domain Event
