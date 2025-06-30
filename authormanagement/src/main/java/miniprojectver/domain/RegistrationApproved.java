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

    private Long  authorId;
    private Long memberId;
    private Date checkedDate;
    private String authorRole;

    public RegistrationApproved(Author aggregate) {
        super(aggregate);
    }

    public RegistrationApproved() {
        super();
    }
}
//>>> DDD / Domain Event
