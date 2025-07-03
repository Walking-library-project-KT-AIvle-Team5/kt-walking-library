package miniprojectver.domain;

import miniprojectver.infra.AbstractEvent;

public class MemberSignedUp extends AbstractEvent {

    private Long id;
    private String loginId;
    private String name;
    private YesNo isKtCustomer;
    private String role;
    private String userId; // ✅ [추가] 팀원이 요청한 userId 필드

    public MemberSignedUp(Member aggregate){
        super(aggregate);
        // ✅ [추가] 이벤트가 생성될 때 Member 객체의 필드 값을 복사하고,
        // loginId를 userId 필드에 할당합니다.
        if (aggregate != null) {
            this.id = aggregate.getId();
            this.loginId = aggregate.getLoginId();
            this.name = aggregate.getName();
            this.isKtCustomer = aggregate.getIsKtCustomer();
            this.role = aggregate.getRole();
            this.userId = aggregate.getLoginId(); // loginId 값을 userId에 복사
        }
    }
    
    public MemberSignedUp(){
        super();
    }

    // --- Getters ---
    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public YesNo getIsKtCustomer() {
        return isKtCustomer;
    }

    public String getRole() {
        return role;
    }

    public String getUserId() { // ✅ [추가] userId 필드의 Getter
        return userId;
    }
}