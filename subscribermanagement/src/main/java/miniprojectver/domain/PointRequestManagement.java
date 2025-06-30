// src/main/java/miniprojectver/domain/PointRequestManagement.java
package miniprojectver.domain;

// import com.fasterxml.jackson.databind.ObjectMapper; // 사용하지 않으면 제거 가능
// import java.time.LocalDate; // 사용하지 않으면 제거 가능
// import java.util.Collections; // 사용하지 않으면 제거 가능
// import java.util.Date; // Date 대신 LocalDateTime 사용하므로 제거
// import java.util.List; // 사용하지 않으면 제거 가능
// import java.util.Map; // 사용하지 않으면 제거 가능
import java.math.BigDecimal;
import java.time.LocalDateTime; // LocalDateTime 사용을 위해 임포트
import javax.persistence.*;
import lombok.Data;
// import miniprojectver.SubscribermanagementApplication; // 사용하지 않으면 제거 가능
import miniprojectver.domain.PointPurchaseRequested; // 이벤트 클래스 임포트
import miniprojectver.domain.PointRequestStatus; // 새로 정의한 Enum 임포트

@Entity
@Table(name = "point_request_management") // 테이블명은 일반적으로 스네이크 케이스로 작성
@Data // Lombok을 통해 getter, setter, toString, equals, hashCode 자동 생성
//<<< DDD / Aggregate Root
public class PointRequestManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // ID 생성 전략
    private Long pointRequestId; // 포인트 요청 ID (애그리게이트 루트 식별자)

    private String userId; // 사용자 ID

    private Long requestedPointAmount; // 요청된 포인트 양

    private String paymentMethodId; // 결제 수단 ID

    private BigDecimal actualPaymentAmount; // 실제 결제 금액

    @Enumerated(EnumType.STRING) // Enum 이름을 문자열로 데이터베이스에 저장
    private PointRequestStatus status; // <-- String -> PointRequestStatus 타입 변경

    @Column(name = "timestamp", columnDefinition = "TIMESTAMP") // DB 컬럼명 명시 및 타입 정의
    private LocalDateTime timestamp; // <-- Date -> LocalDateTime 타입 변경

    // JPA를 위한 기본 생성자 (protected로 선언하여 외부 직접 생성 제어)
    protected PointRequestManagement() {
    }

    // --- [1] 비즈니스 행위를 나타내는 팩토리 메서드 (Command: "포인트 구매 요청") ---
    public static PointRequestManagement requestPointPurchase(
        String userId,
        Long requestedPointAmount,
        String paymentMethodId,
        BigDecimal actualPaymentAmount
    ) {
        // [1-1] Command 유효성 검증
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        if (requestedPointAmount == null || requestedPointAmount <= 0) {
            throw new IllegalArgumentException("Requested point amount must be positive.");
        }
        if (paymentMethodId == null || paymentMethodId.isEmpty()) {
            throw new IllegalArgumentException("Payment method ID cannot be null or empty.");
        }
        if (actualPaymentAmount == null || actualPaymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Actual payment amount must be positive.");
        }

        // [1-2] 애그리게이트 객체 생성 및 초기 상태 설정
        PointRequestManagement newRequest = new PointRequestManagement();
        newRequest.setUserId(userId);
        newRequest.setRequestedPointAmount(requestedPointAmount);
        newRequest.setPaymentMethodId(paymentMethodId);
        newRequest.setActualPaymentAmount(actualPaymentAmount);
        newRequest.setStatus(PointRequestStatus.PENDING); // <-- Enum 값으로 할당
        newRequest.setTimestamp(LocalDateTime.now()); // <-- LocalDateTime.now() 사용

        // [1-3] 이벤트 발행은 @PostPersist 훅에서 처리하여 ID가 할당된 후 이루어지도록 합니다.
        // 여기서는 직접 publishAfterCommit()을 호출하지 않습니다.
        return newRequest;
    }

    // --- [2] JPA 라이프사이클 훅: @PostPersist ---
    // 엔티티가 데이터베이스에 영속화(DB에 저장)된 직후 호출됩니다.
    // 이 시점에는 pointRequestId (ID)가 이미 할당되어 있습니다.
    @PostPersist
    public void onPostPersist() {
        System.out.println("PointRequestManagement: Running @PostPersist for ID: " + this.pointRequestId);
        // ID가 할당된 상태이므로, 이벤트에 모든 필요한 정보를 담아 발행할 수 있습니다.
        PointPurchaseRequested pointPurchaseRequested = new PointPurchaseRequested(this);
        pointPurchaseRequested.publishAfterCommit(); // <-- 이벤트 발행
    }
}
//>>> DDD / Aggregate Root