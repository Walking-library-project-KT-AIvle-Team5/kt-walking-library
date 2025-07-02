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

    private String authorId;
    private Long memberId;

    public AuthorDeleted(Author aggregate) {
        super(aggregate);
    }

    public AuthorDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
