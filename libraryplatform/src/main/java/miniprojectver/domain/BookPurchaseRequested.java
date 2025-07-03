package miniprojectver.domain;

import java.math.BigDecimal;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)  // 이 한 줄 추가
public class BookPurchaseRequested extends AbstractEvent {
    private Long purchaseRequestId;
    private String userId;
    private String bookId;
    private BigDecimal price;
    private BigDecimal point;
    // timestamp는 상속받아서 사용 (Long 타입 유지)
}
