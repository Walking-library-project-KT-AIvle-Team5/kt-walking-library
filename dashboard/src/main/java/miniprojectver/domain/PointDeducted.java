package miniprojectver.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import miniprojectver.infra.AbstractEvent;

@Data
public class PointDeducted extends AbstractEvent {

    private String userId;
    private Integer amountPoint;
    private String bookId;
    private String subscriptionId;
}
