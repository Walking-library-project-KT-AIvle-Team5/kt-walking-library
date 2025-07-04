package miniprojectver.domain;

import java.util.Date;
import java.util.UUID;
import miniprojectver.infra.AbstractEvent;

public class KtAuthenticationRequested extends AbstractEvent {

    private Long memberId;
    private String verificationId;
    private Date requestedAt;

    // 기본 생성자
    public KtAuthenticationRequested() {
        this.verificationId = UUID.randomUUID().toString();
        this.requestedAt = new Date();
    }
    
    // MemberManagement 객체를 받는 생성자
    public KtAuthenticationRequested(MemberManagement aggregate) {
        // ✅ [수정] super()를 먼저 호출하고, 필요한 값들을 직접 설정합니다.
        super(aggregate);
        this.verificationId = UUID.randomUUID().toString();
        this.requestedAt = new Date();

        if (aggregate != null) {
            this.setMemberId(aggregate.getId());
        }
    }

    // --- Getter and Setter ---
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }
}