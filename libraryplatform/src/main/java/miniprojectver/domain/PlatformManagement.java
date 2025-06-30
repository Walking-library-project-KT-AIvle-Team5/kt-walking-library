package miniprojectver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import miniprojectver.LibraryplatformApplication;
import miniprojectver.domain.PaymentRecommendationMessageSent;
import miniprojectver.domain.SubscriptionStatusChecked;


import miniprojectver.domain.PointUseFailed;

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

    @Data
    public class FetchSubscriberList {
    private String userId;
    private String status; // "ACTIVE" 등
}


    public static PlatformManagementRepository repository() {
        PlatformManagementRepository platformManagementRepository = LibraryplatformApplication.applicationContext.getBean(
            PlatformManagementRepository.class
        );
        return platformManagementRepository;
    }

    //<<< Clean Arch / Port Method
public static void checkSubscription(BookPurchaseRequested bookPurchaseRequested) {
    String userId = bookPurchaseRequested.getUserId();

    // RestTemplate 사용
    RestTemplate restTemplate = LibraryplatformApplication.applicationContext.getBean(RestTemplate.class);

    // subscriberService.url 은 application.yml에 정의되어 있어야 함
    String url = "http://localhost:8081/subscribers?userId=" + userId;

    boolean isSubscribed = false;

    try {
        // 예시: 응답이 boolean 또는 Subscriber 객체 배열이라고 가정
        ResponseEntity<FetchSubscriberList[]> response =
            restTemplate.getForEntity(url, FetchSubscriberList[].class);

        FetchSubscriberList[] subscribers = response.getBody();

        if (subscribers != null && subscribers.length > 0) {
            for (FetchSubscriberList sub : subscribers) {
                if ("ACTIVE".equalsIgnoreCase(sub.getStatus())) {
                    isSubscribed = true;
                    break;
                }
            }
        }
    } catch (Exception e) {
        System.out.println("구독자 정보 조회 실패: " + e.getMessage());
    }

    // 이벤트 발행
    SubscriptionStatusChecked event = new SubscriptionStatusChecked();
    event.setUserId(userId);
    event.setIsSubscribed(isSubscribed);
    event.publishAfterCommit();
}

    
    //>>> Clean Arch / Port Method
    public static void sendPaymentRecommendation(PointUseFailed pointUseFailed) {

    String userId = pointUseFailed.getUserId();
    Boolean  isKtCustomer = pointUseFailed.getIsktCustomer(); 

    // KT 고객이 아닌 경우에만 추천 메시지 전송
    if (Boolean.TRUE.equals(isKtCustomer)) {
        // 조건 불충족: 아무 작업 안 함
        return;
    }

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
