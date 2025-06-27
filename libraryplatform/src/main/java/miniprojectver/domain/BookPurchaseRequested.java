package miniprojectver.domain;

import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
public class BookPurchaseRequested extends AbstractEvent {

    private Long purchaseRequestId;
    private String userId;
    private String bookId;
    private Object price;
    private Object point;
    private Date timestamp;
}
