package miniprojectver.domain;
 
import miniprojectver.infra.AbstractEvent;
 
public class MemberSignedUp extends AbstractEvent {
 
    private Long id;
    private String loginId;
    private String name;
    private YesNo isKtCustomer;
    private String role;
   
    // ✅ [추가] userId 필드
    private String userId;
 
    public MemberSignedUp(Member aggregate){
        super(aggregate);
    }
    public MemberSignedUp(){
        super();
    }
 
    // --- Getters and Setters ---
   
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
 
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
 
    public YesNo getIsKtCustomer() {
        return isKtCustomer;
    }
    public void setIsKtCustomer(YesNo isKtCustomer) {
        this.isKtCustomer = isKtCustomer;
    }
   
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
 
    // ✅ [추가] userId의 Getter와 Setter
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}