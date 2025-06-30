package miniprojectver.domain;

import javax.persistence.*;
<<<<<<< HEAD
=======
import javax.validation.constraints.NotNull;
>>>>>>> 2dcf9af (서브모듈 변경사항 커밋)
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
<<<<<<< HEAD
    private Boolean isKtCustomer;
=======

    @NotNull(message = "KT 고객 여부(isKtCustomer)는 필수 입력 항목입니다.")
    // 1. 필드의 타입을 Boolean에서 우리가 만들 YesNo Enum으로 변경합니다.
    // JPA가 이 필드를 데이터베이스와 변환하는 로직은 YesNoConverter.java에서 처리합니다.
    // YesNoConverter에 @Converter(autoApply = true) 설정을 할 것이므로,
    // 여기에 @Convert 어노테이션은 생략할 수 있습니다.
    private YesNo isKtCustomer;

>>>>>>> 2dcf9af (서브모듈 변경사항 커밋)
    private String role;
    private String status;

    @PostPersist
    public void onPostPersist(){
        MemberSignedUp memberSignedUp = new MemberSignedUp();
        BeanUtils.copyProperties(this, memberSignedUp);
        memberSignedUp.publishAfterCommit();
    }

    // Lombok 컴파일 에러 방지를 위한 Getter/Setter 수동 추가
<<<<<<< HEAD
=======
    // (@Data 어노테이션이 정상 동작한다면 이 부분은 제거해도 됩니다.)
>>>>>>> 2dcf9af (서브모듈 변경사항 커밋)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
<<<<<<< HEAD
=======
//asdfasdfasdf

>>>>>>> 2dcf9af (서브모듈 변경사항 커밋)
