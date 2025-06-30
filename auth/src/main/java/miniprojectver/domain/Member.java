package miniprojectver.domain;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import lombok.Data;

@Entity
@Table(name="Member_table")
@Data
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
        // 이 코드가 정상 동작하려면 AbstractEvent와 MemberSignedUp 클래스가 올바르게 생성되어 있어야 합니다.
        memberSignedUp.publishAfterCommit();
    }
}
