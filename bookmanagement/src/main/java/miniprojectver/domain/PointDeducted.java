package miniprojectver.domain;

import lombok.Data;
import miniprojectver.infra.AbstractEvent;

@Data
public class PointDeducted extends AbstractEvent {

    private String userId;
    private Integer amountPoint;
    private Long bookId;
}
