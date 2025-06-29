package miniprojectver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniprojectver.LibraryplatformApplication;
import miniprojectver.domain.PaymentRecommendationMessageSent;
import miniprojectver.domain.SubscriptionStatusChecked;

@Entity
@Table(name = "PlatformManagement_table")
@Data
//<<< DDD / Aggregate Root
public class PlatformManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subscribedUserIds;

    private String recommendationMessage;

    public static PlatformManagementRepository repository() {
        PlatformManagementRepository platformManagementRepository = LibraryplatformApplication.applicationContext.getBean(
            PlatformManagementRepository.class
        );
        return platformManagementRepository;
    }

    //<<< Clean Arch / Port Method
    public static void checkSubscription(
        BookPurchaseRequested bookPurchaseRequested
    ) {
        String userId = bookPurchaseRequested.getUserId();

        // FetchSubscriberListRepository 가져오기
        FetchSubscriberListRepository subscriberRepo =
            LibraryplatformApplication.applicationContext.getBean(FetchSubscriberListRepository.class);

        // 해당 userId의 ACTIVE 구독 여부 확인
        List<FetchSubscriberList> activeSubs = subscriberRepo.findByUserIdAndStatus(userId, "ACTIVE");
        boolean isSubscribed = (activeSubs != null && !activeSubs.isEmpty());

        // SubscriptionStatusChecked 이벤트 발행
        SubscriptionStatusChecked event = new SubscriptionStatusChecked();
        event.setUserId(userId);
        event.setIsSubscribed(isSubscribed);
        event.publishAfterCommit();


    }
    
    //>>> Clean Arch / Port Method
    public static void sendPaymentRecommendation(PointUseFailed pointUseFailed) {

    String userId = pointUseFailed.getUserId();

    PlatformManagement platformManagement = new PlatformManagement();
    platformManagement.setSubscribedUserIds(userId);
    platformManagement.setRecommendationMessage("포인트가 부족합니다. 구독 요금제를 추천합니다.");

    repository().save(platformManagement);

    PaymentRecommendationMessageSent event = new PaymentRecommendationMessageSent(platformManagement);
    event.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
