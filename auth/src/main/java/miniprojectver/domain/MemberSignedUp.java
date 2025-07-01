package miniprojectver.domain;

import miniprojectver.infra.AbstractEvent;
import lombok.Data;

@Data
public class MemberSignedUp extends AbstractEvent {

    private Long id;
    private String loginId;
    private String name;
    private YesNo isKtCustomer; // 타입을 YesNo로 변경
    private String role;

    public MemberSignedUp(Member aggregate){
        super(aggregate);
    }
    public MemberSignedUp(){
        super();
    }
}
