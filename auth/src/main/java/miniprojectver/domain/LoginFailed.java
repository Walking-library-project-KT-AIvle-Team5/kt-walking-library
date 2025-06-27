package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class LoginFailed extends AbstractEvent {

    private String loginId;
    private String failureReason;
    private Date failedAt;
    private String ipAddress;
    private String userAgent;

    public LoginFailed(MemberManagement aggregate) {
        super(aggregate);
    }

    public LoginFailed() {
        super();
    }
}
//>>> DDD / Domain Event
