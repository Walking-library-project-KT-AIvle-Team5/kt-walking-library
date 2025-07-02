package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

import java.util.Date;

/**
 * 포인트관리서비스 → 구독자관리서비스
 * 구독 활성화됨 이벤트
 */
@Getter @Setter
@NoArgsConstructor
public class SubscriptionActivated extends AbstractEvent {

    private String userId;
    private Date startedAt;
    private Date endsAt;
    private Boolean isSubscribed;
    private String status;  // 예: "ACTIVATE"

    
}
