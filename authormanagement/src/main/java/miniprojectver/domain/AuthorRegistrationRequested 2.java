package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorRegistrationRequested extends AbstractEvent {

    private Long authorId;
    private String authorName;
    private String authorInfo;

    public AuthorRegistrationRequested(AuthorRegistration aggregate) {
        super(aggregate);
    }

    public AuthorRegistrationRequested() {
        super();
    }
}
//>>> DDD / Domain Event
