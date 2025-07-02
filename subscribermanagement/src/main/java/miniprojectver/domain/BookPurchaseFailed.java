package miniprojectver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import miniprojectver.infra.AbstractEvent;

// BookPurchaseStatus Enum을 사용하기 위해 임포트합니다.
import miniprojectver.domain.BookPurchaseStatus;

@Getter
@ToString
@NoArgsConstructor
public class BookPurchaseFailed extends AbstractEvent {

    private Long purchaseRequestId;
    private String userId;
    private String bookId;
    private BigDecimal price;
    private BigDecimal point;
    private LocalDateTime failedAt;    // 실패 시각

    // String에서 BookPurchaseStatus Enum 타입으로 변경
    private BookPurchaseStatus status;

    private String reason;    // 실패 이유 (선택 사항, 필요시 주석 해제)

    public BookPurchaseFailed(BookPurchaseManagement aggregate) {
        super();
        this.purchaseRequestId = aggregate.getPurchaseRequestId();
        this.userId = aggregate.getUserId();
        this.bookId = aggregate.getBookId();
        this.price = aggregate.getPrice();
        this.point = aggregate.getPoint();
        this.failedAt = aggregate.getProcessedAt(); // 애그리게이트의 processedAt 사용
        
        // aggregate.getStatus()는 이미 BookPurchaseStatus 타입이므로 그대로 할당
        this.status = aggregate.getStatus(); 
        
    }
}