package miniprojectver.domain;
import lombok.Data;

import java.util.Date;

@Data
public class PointDeducted {
    private String userId;
    private String bookId;
    private String authorId;
    private Integer amount;
    private Date purchasedAt;
}
