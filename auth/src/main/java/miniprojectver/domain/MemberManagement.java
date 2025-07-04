package miniprojectver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniprojectver.AuthApplication;
import miniprojectver.domain.KtAuthenticationRequested;
import miniprojectver.domain.LoginFailed;
import miniprojectver.domain.Loginsuccessed;
import miniprojectver.domain.MemberJoined;

@Entity
@Table(name = "MemberManagement_table")
@Data
public class MemberManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean isKtCustomer;

    private String loginId;

    private String password;

    private String name;

    private String role;

    private String status;

    @PostPersist
    public void onPostPersist() {
        KtAuthenticationRequested ktAuthenticationRequested = new KtAuthenticationRequested(
            this
        );
        ktAuthenticationRequested.publishAfterCommit();

        Loginsuccessed loginsuccessed = new Loginsuccessed(this);
        loginsuccessed.publishAfterCommit();

        MemberJoined memberJoined = new MemberJoined(this);
        memberJoined.publishAfterCommit();

        LoginFailed loginFailed = new LoginFailed(this);
        loginFailed.publishAfterCommit();
    }

    public static MemberManagementRepository repository() {
        MemberManagementRepository memberManagementRepository = AuthApplication.applicationContext.getBean(
            MemberManagementRepository.class
        );
        return memberManagementRepository;
    }

    // ✅ [추가] 컴파일 에러 해결을 위해 getId() 메소드를 명시적으로 추가합니다.
    public Long getId() {
        return this.id;
    }
}