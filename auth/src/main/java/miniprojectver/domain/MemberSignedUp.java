package miniprojectver.domain;


import lombok.Data;

@Data
public class MemberSignedUp extends AbstractEvent {

    private Long id;
    private String loginId;
    private String name;

    private String role;

    public MemberSignedUp(Member aggregate){
        super(aggregate);
    }
    public MemberSignedUp(){
        super();
    }
}
