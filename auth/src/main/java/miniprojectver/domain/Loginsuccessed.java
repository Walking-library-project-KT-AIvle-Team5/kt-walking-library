package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class Loginsuccessed extends AbstractEvent {

    private Long id;
    private String role;
    private Date loginAt;
    private String ipAddress;
    private String userAgent;

    public Loginsuccessed(MemberManagement aggregate) {
        super(aggregate);
    }

    public Loginsuccessed() {
        super();
    }
}
//>>> DDD / Domain Event
