package miniprojectver.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BookPurchaseRequested extends AbstractEvent {

    private Long purchaseRequestId;
    private String userId;
    private String bookId;
    private BigDecimal price;
    private BigDecimal point;
    private Date timestamp;

    public BookPurchaseRequested(BookPurchaseManagement aggregate) {
        super(aggregate);
    }

    public BookPurchaseRequested() {
        super();
    }
}
//>>> DDD / Domain Event
