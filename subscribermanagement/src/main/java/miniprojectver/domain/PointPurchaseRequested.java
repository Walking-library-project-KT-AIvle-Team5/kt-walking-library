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
public class PointPurchaseRequested extends AbstractEvent {

    private Long pointRequestId;
    private String userId;
    private Long requestedPointAmount;
    private String paymentMethodId;
    private BigDecimal actualPaymentAmount;

    public PointPurchaseRequested(PointRequestManagement aggregate) {
        super(aggregate);
    }

    public PointPurchaseRequested() {
        super();
    }
}
//>>> DDD / Domain Event
