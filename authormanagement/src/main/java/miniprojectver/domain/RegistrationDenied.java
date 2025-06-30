package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class RegistrationDenied extends AbstractEvent {

    private Long authorId;
    private Long memberId;
    private Date checkedDate;
    private String denialReason;
    private String authorRole;

    public RegistrationDenied(Author aggregate) {
        super(aggregate);
    }

    public RegistrationDenied() {
        super();
    }
}
//>>> DDD / Domain Event
