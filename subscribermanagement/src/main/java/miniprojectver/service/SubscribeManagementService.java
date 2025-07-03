package miniprojectver.service;

import java.util.Date;
import java.util.Optional; // Optional 임포트 추가
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

    // --- [Query Handler: 구독 여부 조회] ---
    // 사용자 ID로 구독 정보를 조회하는 메서드를 추가합니다.
    public Optional<SubscribeManagement> getSubscriptionByUserId(String userId) {
        // SubscribeManagementRepository에 findByUserId 메서드가 정의되어 있어야 합니다.
        return subscribeManagementRepository.findByUserId(userId);
    }

    // --- [Command Handler: 구독 요청] ---
    public SubscribeManagement requestNewSubscription(String userId) {
        SubscribeManagement newSubscription = SubscribeManagement.requestSubscription(
            userId
        );
        subscribeManagementRepository.save(newSubscription);
        return newSubscription;
    }

    // --- [Command Handler: 구독 취소] ---
    public SubscribeManagement cancelExistingSubscription(Long subscriptionId) {
        SubscribeManagement subscription = subscribeManagementRepository.findById(subscriptionId)
            .orElseThrow(() -> new IllegalArgumentException("Subscription not found for ID: " + subscriptionId));

        subscription.cancelSubscription();
        subscribeManagementRepository.save(subscription);
        return subscription;
    }

    // --- [Command Handler: 구독 활성화] ---
    public SubscribeManagement activateSubscription(Long subscriptionId) {
        SubscribeManagement subscription = subscribeManagementRepository.findById(subscriptionId)
            .orElseThrow(() -> new IllegalArgumentException("Subscription not found for ID: " + subscriptionId));

        subscription.activateSubscription();
        subscribeManagementRepository.save(subscription);
        return subscription;
    }
}
