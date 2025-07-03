package miniprojectver.domain;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoined extends PointAmount {

    private String userId;
    private boolean isKtCustomer;

    public MemberJoined(String userId, boolean isKtCustomer) {
        super(BigDecimal.valueOf(1000));  // 기본 지급
        this.userId = userId;
        this.isKtCustomer = isKtCustomer;
    }
}
