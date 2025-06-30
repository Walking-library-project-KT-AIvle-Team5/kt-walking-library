package miniprojectver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Calendar; // Date 연산을 위해 Calendar 클래스 추가
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor; // JPA를 위한 기본 생성자 (protected 접근 제어자)
import lombok.Getter;       // 필드에 대한 getter만 자동 생성
import lombok.AccessLevel; // NoArgsConstructor의 접근 제어자 설정을 위함
import lombok.EqualsAndHashCode; // equals/hashCode 명시적 제어 (subscriptionId만 사용)
import miniprojectver.SubscribermanagementApplication;
import miniprojectver.domain.SubscriptionCancelled;
import miniprojectver.domain.SubscriptionRequested;

@Entity
@Table(name = "subscribe_management")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "subscriptionId", callSuper = false)
//<<< DDD / Aggregate Root
public class SubscribeManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscriptionId; // 구독 ID (애그리게이트 루트 식별자) - 자동 생성

    private String userId; // 사용자 ID - 사용자로부터 입력

    private String status; // 구독 상태 (예: "PENDING_REQUEST", "ACTIVE", "CANCELLED") - 로직상 자동 설정

    private Date startedAt; // 구독 시작일 - 구독 요청 시 자동으로 현재 시간 입력

    private Date endsAt; // 구독 종료일 - startedAt 기준으로 한 달 뒤로 자동 설정

    // --- [1] 비즈니스 행위: 구독 요청 (Command) - 팩토리 메서드 ---
    // 새로운 구독을 생성하고 초기 상태를 설정하는 유일한 방법
    // userId만 외부에서 입력받고, startedAt과 endsAt은 로직으로 자동 설정
    public static SubscribeManagement requestSubscription(String userId) {
        // [1-1] 불변 조건/Command 유효성 검증
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }

        // [1-2] 애그리게이트 객체 생성 및 초기 상태 설정
        SubscribeManagement newSubscription = new SubscribeManagement();
        newSubscription.userId = userId;

        // startedAt은 현재 시간으로 자동 설정
        newSubscription.startedAt = new Date(); // 현재 시각

        // endsAt은 startedAt으로부터 1개월 뒤로 자동 설정
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newSubscription.startedAt);
        calendar.add(Calendar.MONTH, 1); // 1개월 추가
        newSubscription.endsAt = calendar.getTime();

        newSubscription.status = "PENDING_REQUEST"; // 초기 상태는 '요청 대기 중'

        // Note: SubscriptionRequested 이벤트는 @PostPersist 훅에서 발행됩니다.
        // 이는 subscriptionId가 생성된 후에 이벤트에 포함될 수 있도록 합니다.
        return newSubscription;
    }

    // --- [2] 비즈니스 행위: 구독 취소 (Command) ---
    public void cancelSubscription() {
        // [2-1] 불변 조건 검사
        if ("CANCELLED".equals(this.status)) { // 이미 취소된 경우
            throw new IllegalStateException("Subscription is already cancelled.");
        }
        // ACTIVE 또는 PENDING_REQUEST 상태가 아니면 취소 불가능
        if (!"ACTIVE".equals(this.status) && !"PENDING_REQUEST".equals(this.status)) {
             throw new IllegalStateException("Subscription can only be cancelled from ACTIVE or PENDING_REQUEST status. Current: " + this.status);
        }

        // [2-2] 상태 변경 (setter는 사용하지 않고 직접 필드 변경)
        this.status = "CANCELLED";
        // [2-3] 도메인 이벤트 발행
        new SubscriptionCancelled(this).publishAfterCommit();
    }

    // --- [3] 비즈니스 행위: 구독 활성화 (Command - 외부 이벤트에 반응) ---
    // PENDING_REQUEST 상태의 구독이 결제 완료 등으로 인해 ACTIVE 상태로 전환될 때 사용
    public void activateSubscription() {
        // [3-1] 불변 조건 검사
        if (!"PENDING_REQUEST".equals(this.status)) {
            throw new IllegalStateException("Subscription can only be activated from PENDING_REQUEST status. Current: " + this.status);
        }

        // [3-2] 상태 변경
        this.status = "ACTIVE"; // 상태를 '활성'으로 변경
        // [3-3] 도메인 이벤트 발행
        new SubscriptionActivated(this).publishAfterCommit();
    }


    // --- [JPA 라이프사이클 훅] ---
    @PostPersist
    public void onPostPersist() {
        System.out.println("SubscribeManagement: Running @PostPersist for ID: " + this.subscriptionId);
        // 애그리게이트가 처음 생성되어 DB에 저장될 때만 'SubscriptionRequested' 이벤트를 발행합니다.
        // 이 이벤트는 구독 요청이 접수되었음을 알립니다.
        SubscriptionRequested subscriptionRequested = new SubscriptionRequested(this);
        subscriptionRequested.publishAfterCommit();
    }
}
//>>> DDD / Aggregate Root
