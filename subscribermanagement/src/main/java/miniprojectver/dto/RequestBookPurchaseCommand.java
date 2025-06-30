// src/main/java/miniprojectver/infra/RequestBookPurchaseCommand.java
package miniprojectver.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RequestBookPurchaseCommand {
    private String userId;
    private String bookId;
    private BigDecimal price;
    private BigDecimal point;
}