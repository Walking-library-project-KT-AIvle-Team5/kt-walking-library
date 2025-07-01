package miniprojectver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
// import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;

// import miniprojectver.domain.BookPurchaseRequested;
// import miniprojectver.domain.BookPurchaseCompleted;
// import miniprojectver.domain.BookPurchaseFailed;
// import miniprojectver.domain.BookPurchaseStatus; // <- BookPurchaseStatus Enum 임포트

@Entity
@Table(name = "book_purchase_management")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "purchaseRequestId", callSuper = false)
//<<< DDD / Aggregate Root
public class BookPurchaseManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long purchaseRequestId;

    private String userId;
    private String bookId;
    private BigDecimal price;
    private BigDecimal point;

    @Enumerated(EnumType.STRING) // <- 이 어노테이션 추가
    @Column(name = "purchase_status")
    private BookPurchaseStatus status; // <- 타입 변경: String -> BookPurchaseStatus

    @Column(name = "started_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime startedAt;
    
    @Column(name = "processed_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime processedAt;

    // --- [1] 비즈니스 행위: 도서 구매 요청 (Command) - 팩토리 메서드 ---
    public static BookPurchaseManagement requestBookPurchase(
        String userId,
        String bookId,
        BigDecimal price,
        BigDecimal point
    ) {
        // [1-1] Command 유효성 검증 (변경 없음)
        if (userId == null || userId.isEmpty()) throw new IllegalArgumentException("User ID cannot be null or empty.");
        if (bookId == null || bookId.isEmpty()) throw new IllegalArgumentException("Book ID cannot be null or empty.");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive.");
        }
        if (point == null || point.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Point must be non-negative.");
        }

        // [1-2] 애그리게이트 객체 생성 및 초기 상태 설정
        BookPurchaseManagement newPurchase = new BookPurchaseManagement();
        newPurchase.userId = userId;
        newPurchase.bookId = bookId;
        newPurchase.price = price;
        newPurchase.point = point;
        newPurchase.status = BookPurchaseStatus.PENDING; // <- Enum 값으로 변경
        newPurchase.startedAt = LocalDateTime.now();

        // [1-3] 도서 구매 요청 이벤트 발행 <--- 이 부분 추가!
        new BookPurchaseRequested(newPurchase).publishAfterCommit();

        return newPurchase;
    }

    // --- [2] 비즈니스 행위: 도서 구매 완료 (Command - 외부 시스템 응답) ---
    public void completePurchase() {
        // [2-1] 불변 조건 검사
        // Enum 값 비교는 `==` 또는 `equals()` 사용. 여기서는 `==`이 더 간결하고 안전.
        if (this.status != BookPurchaseStatus.PENDING) { // <- Enum 값으로 변경
            throw new IllegalStateException("Book purchase can only be completed from PENDING status. Current: " + this.status);
        }

        // [2-2] 상태 변경
        this.status = BookPurchaseStatus.COMPLETED; // <- Enum 값으로 변경
        this.processedAt = LocalDateTime.now();

        // [2-3] 도메인 이벤트 발행 (변경 없음)
        new BookPurchaseCompleted(this).publishAfterCommit();
    }

    // --- [3] 비즈니스 행위: 도서 구매 실패 (Command - 외부 시스템 응답) ---
    public void failPurchase(String reason) {
        // [3-1] 불변 조건 검사
        if (this.status != BookPurchaseStatus.PENDING) { // <- Enum 값으로 변경
            throw new IllegalStateException("Book purchase can only be failed from PENDING status. Current: " + this.status);
        }

        // [3-2] 상태 변경
        this.status = BookPurchaseStatus.FAILED; // <- Enum 값으로 변경
        this.processedAt = LocalDateTime.now();

        // [3-3] 도메인 이벤트 발행 (변경 없음)
        new BookPurchaseFailed(this).publishAfterCommit();
    }

    // --- [JPA 라이프사이클 훅] ---
    @PostPersist
    public void onPostPersist() {
        System.out.println("BookPurchaseManagement: Running @PostPersist for ID: " + this.purchaseRequestId);
    }
}
//>>> DDD / Aggregate Root