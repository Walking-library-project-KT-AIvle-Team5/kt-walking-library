package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class KtAuthenticationRequested extends AbstractEvent {

    private Long id;
    private String verificationId;
    private Date requestedAt;

    public KtAuthenticationRequested(MemberManagement aggregate) {
        super(aggregate);
    }

    public KtAuthenticationRequested() {
        super();
    }
}
//>>> DDD / Domain Event
