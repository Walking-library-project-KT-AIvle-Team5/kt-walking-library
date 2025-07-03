// subscribermanagement/src/main/java/miniprojectver/infra/PointDeductedEventListener.java

package miniprojectver.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import miniprojectver.domain.PointDeducted; // PointDeducted DTO 임포트
import miniprojectver.domain.SubscribeManagement;
import miniprojectver.domain.SubscribeManagementRepository;

import java.util.List; // 여러 개의 구독이 있을 수 있으므로 List로 받습니다.

@Service
public class PointDeductedEventListener {

    @Autowired
    private SubscribeManagementRepository subscribeManagementRepository;

    @StreamListener(target = "event-in") // subscribermanagement의 application.yml에 정의된 input 채널 이름
    @Transactional
    public void handlePointDeductedEvent(@Payload PointDeducted event) {
        System.out.println("=================================================");
        System.out.println("PointDeducted 이벤트 수신: " + event.toString());
        System.out.println("=================================================");

        if ("SUBSCRIPTION".equals(event.getReason())) {
            System.out.println("-> 구독 관련 포인트 차감 이벤트이므로 처리 시작.");

            // **** 중요: relatedSubscriptionId가 없으므로 userId와 PENDING_REQUEST 상태로 찾습니다. ****
            // 이 방법은 한 사용자가 여러 PENDING_REQUEST 구독을 가질 경우 모호할 수 있습니다.
            // 리포지토리에 이 메서드를 추가해야 합니다: List<SubscribeManagement> findByUserIdAndStatus(String userId, String status);
            List<SubscribeManagement> pendingSubscriptions = 
                subscribeManagementRepository.findByUserIdAndStatus(event.getUserId(), "PENDING_REQUEST");

            if (!pendingSubscriptions.isEmpty()) {
                // *** 여기서 가장 중요한 비즈니스 로직 판단이 필요합니다 ***
                // 1. 만약 하나의 PENDING_REQUEST만 기대한다면:
                if (pendingSubscriptions.size() == 1) {
                    SubscribeManagement subscription = pendingSubscriptions.get(0);
                    try {
                        subscription.activateSubscription();
                        subscribeManagementRepository.save(subscription);
                        System.out.println("-> 구독 ID " + subscription.getSubscriptionId() + "이(가) 포인트 차감 확인 후 'ACTIVE'로 성공적으로 변경되었습니다.");
                    } catch (IllegalStateException e) {
                        System.err.println("-> 오류: 구독 ID " + subscription.getSubscriptionId() + " 활성화 실패 - " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("-> 오류: 구독 ID " + subscription.getSubscriptionId() + " 활성화 중 예상치 못한 오류 발생: " + e.getMessage());
                    }
                } else {
                    // 2. 여러 PENDING_REQUEST 구독이 발견된 경우 (모호성 발생)
                    System.out.println("-> 경고: 사용자 " + event.getUserId() + "에게 PENDING_REQUEST 상태의 구독이 " + pendingSubscriptions.size() + "개 발견되었습니다. " +
                                       "어떤 구독을 활성화해야 할지 모호합니다. 첫 번째 구독을 활성화합니다 (신중해야 함!).");
                    // 비즈니스 규칙에 따라 '가장 오래된' 또는 '가장 최근에 생성된' 구독을 선택하는 등의 추가 로직이 필요할 수 있습니다.
                    // 현재는 편의상 첫 번째 구독을 선택합니다.
                    SubscribeManagement subscriptionToActivate = pendingSubscriptions.get(0); 
                    try {
                        subscriptionToActivate.activateSubscription();
                        subscribeManagementRepository.save(subscriptionToActivate);
                        System.out.println("-> 구독 ID " + subscriptionToActivate.getSubscriptionId() + "이(가) 포인트 차감 확인 후 'ACTIVE'로 성공적으로 변경되었습니다.");
                    } catch (IllegalStateException e) {
                        System.err.println("-> 오류: 구독 ID " + subscriptionToActivate.getSubscriptionId() + " 활성화 실패 - " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("-> 오류: 구독 ID " + subscriptionToActivate.getSubscriptionId() + " 활성화 중 예상치 못한 오류 발생: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("-> 사용자 " + event.getUserId() + "에게 PENDING_REQUEST 상태의 구독을 찾을 수 없습니다. 이벤트 처리 건너뛰기.");
            }
        } else {
            // 'SUBSCRIPTION' 목적이 아닌 포인트 차감 이벤트는 무시
            System.out.println("-> 'SUBSCRIPTION' 목적이 아닌 이벤트이므로 구독 상태 변경을 건너뜁니다. Reason: " + event.getReason());
        }
    }
}
