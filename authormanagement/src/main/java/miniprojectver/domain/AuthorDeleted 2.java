package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorDeleted extends AbstractEvent {

    private Long authorId;
    private String authorName;
    private String authorInfo;
    private String authorPortfolio;
    private Boolean authorRole;
    private Date craeteDate;

    public AuthorDeleted(AuthorRegistration aggregate) {
        super(aggregate);
    }

    public AuthorDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
