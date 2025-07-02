package miniprojectver.domain;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import miniprojectver.infra.AbstractEvent;

@Getter
@ToString
@NoArgsConstructor
public class SubscriptionActivated extends AbstractEvent {

    private Long subscriptionId;
    private String userId;
    private Date activatedAt; // 활성화된 시점
    private Date startedAt;   // 실제 구독 시작일
    private Date endsAt;      // 구독 종료일 (갱신 등 없이 최초 활성화 시점 기준)
    private String status;    // 활성화된 후의 상태 (ACTIVE)

    public SubscriptionActivated(SubscribeManagement aggregate) {
        super();
        this.subscriptionId = aggregate.getSubscriptionId();
        this.userId = aggregate.getUserId();
        this.activatedAt = new Date(); // 이벤트 발생 시점
        this.startedAt = aggregate.getStartedAt();
        this.endsAt = aggregate.getEndsAt();
        this.status = aggregate.getStatus(); // "ACTIVE" 상태
    }
}