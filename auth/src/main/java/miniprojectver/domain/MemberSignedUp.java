package miniprojectver.domain;

import miniprojectver.infra.AbstractEvent; // AbstractEvent 클래스를 import 합니다.
import lombok.Data;

@Data
public class MemberSignedUp extends AbstractEvent {

    private Long id;
    private String loginId;
    private String name;
    private Boolean isKtCustomer;
    private String role;

    public MemberSignedUp(Member aggregate){
        super(aggregate);
    }
    public MemberSignedUp(){
        super();
    }
}
