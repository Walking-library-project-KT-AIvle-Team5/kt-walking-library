package miniprojectver.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import miniprojectver.domain.SubscribeManagement;
import miniprojectver.domain.SubscribeManagementRepository;

@Service
@Transactional
public class SubscribeManagementService {

    @Autowired
    private SubscribeManagementRepository subscribeManagementRepository;

    // --- [Command Handler: 구독 요청] ---
    // userId만 입력받도록 변경
    public SubscribeManagement requestNewSubscription(String userId) {
        // 애그리게이트의 팩토리 메서드를 통해 새로운 구독 인스턴스 생성
        SubscribeManagement newSubscription = SubscribeManagement.requestSubscription(
            userId
        );
        // 애그리게이트를 영속화 (이 과정에서 @PostPersist 훅이 호출되어 SubscriptionRequested 이벤트 발행)
        subscribeManagementRepository.save(newSubscription);
        return newSubscription;
    }

    // --- [Command Handler: 구독 취소] ---
    public SubscribeManagement cancelExistingSubscription(Long subscriptionId) {
        // 애그리게이트를 로드
        SubscribeManagement subscription = subscribeManagementRepository.findById(subscriptionId)
            .orElseThrow(() -> new IllegalArgumentException("Subscription not found for ID: " + subscriptionId));

        // 애그리게이트의 비즈니스 메서드 호출 (내부에서 SubscriptionCancelled 이벤트 발행)
        subscription.cancelSubscription();
        // 변경된 애그리게이트 상태 저장
        subscribeManagementRepository.save(subscription);
        return subscription;
    }

    // --- [Command Handler: 구독 활성화] ---
    // 이 메서드는 외부 시스템(예: 결제 서비스)의 '결제 완료' 이벤트에 반응하여 호출될 수 있습니다.
    public SubscribeManagement activateSubscription(Long subscriptionId) {
        SubscribeManagement subscription = subscribeManagementRepository.findById(subscriptionId)
            .orElseThrow(() -> new IllegalArgumentException("Subscription not found for ID: " + subscriptionId));

        subscription.activateSubscription(); // 애그리게이트의 비즈니스 메서드 호출 (내부에서 SubscriptionActivated 이벤트 발행)
        subscribeManagementRepository.save(subscription);
        return subscription;
    }
}