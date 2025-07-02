package miniprojectver.domain;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Member_table")
public class Member {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private Boolean isKtCustomer;
    private String role;
    private String status;

    @PostPersist
    public void onPostPersist(){
        MemberSignedUp memberSignedUp = new MemberSignedUp();
        BeanUtils.copyProperties(this, memberSignedUp);
        memberSignedUp.publishAfterCommit();
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLoginId() { return loginId; }
    public void setLoginId(String loginId) { this.loginId = loginId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Boolean getIsKtCustomer() { return isKtCustomer; }
    public void setIsKtCustomer(Boolean isKtCustomer) { this.isKtCustomer = isKtCustomer; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}