package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class MemberJoined extends AbstractEvent {

    private Long id;
    private Boolean isKtCustomer;
    private String loginId;
    private String password;
    private String name;
    private String role;
    private String status;
    private String registeredAt;

    public MemberJoined(MemberManagement aggregate) {
        super(aggregate);
    }

    public MemberJoined() {
        super();
    }
}
//>>> DDD / Domain Event
