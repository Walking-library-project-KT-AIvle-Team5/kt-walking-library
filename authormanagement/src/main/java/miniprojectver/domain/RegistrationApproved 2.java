package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class RegistrationApproved extends AbstractEvent {

    private Long authorId;
    private String authorName;
    private String authorInfo;
    private Boolean authorRole;
    private Date craeteDate;

    public RegistrationApproved(AuthorRegistration aggregate) {
        super(aggregate);
    }

    public RegistrationApproved() {
        super();
    }
}
//>>> DDD / Domain Event
