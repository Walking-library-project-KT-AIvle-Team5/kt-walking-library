package miniprojectver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookPurchaseRequested extends PointAmount {

    private String userId;
    private String bookId;
    private BigDecimal price;

    @Override
    public BigDecimal getAmount() {
        return price;
    }
}
