package miniprojectver.domain;

import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class MemberJoined extends AbstractEvent {

    private Long id;
    private String userId;
    private Boolean isKtCustomer;
    private String loginId;
    private String password;
    private String name;
    private String role;
    private String status;
    private String registeredAt;
}
