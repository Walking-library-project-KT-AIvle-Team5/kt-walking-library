package miniprojectver.domain;

import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
public class PointDeducted extends AbstractEvent {

    private String userId;
    private Integer amountPoint;
    private String bookId;
    private String subscriptionId;
}
