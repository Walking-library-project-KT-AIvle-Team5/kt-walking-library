package miniprojectver.domain;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import lombok.Data;

// PDF 36~37페이지의 애그리거트 구현 예시를 참고합니다.
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
        memberSignedUp.publishAfterCommit();
    }

    // Lombok 컴파일 에러 방지를 위한 Getter/Setter 수동 추가
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
