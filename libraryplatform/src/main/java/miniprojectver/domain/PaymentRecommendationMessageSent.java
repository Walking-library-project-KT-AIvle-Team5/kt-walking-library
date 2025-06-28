package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PaymentRecommendationMessageSent extends AbstractEvent {

    private Long id;
    private String recommendationMessage;

    public PaymentRecommendationMessageSent(PlatformManagement aggregate) {
        super(aggregate);
    }

    public PaymentRecommendationMessageSent() {
        super();
    }
}
//>>> DDD / Domain Event
