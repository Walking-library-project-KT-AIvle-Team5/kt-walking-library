// src/main/java/miniprojectver/infra/RequestPointPurchaseCommand.java
package miniprojectver.dto;

import lombok.Data;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank; // 유효성 검사 어노테이션
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive; // 0보다 큰 값 검사

@Data // Getter, Setter, toString, equals, hashCode 자동 생성
public class RequestPointPurchaseCommand {

    @NotBlank(message = "User ID cannot be blank") // 비어 있거나 null이 아닌지 검사
    private String userId;

    // @NotNull(message = "Requested point amount cannot be null")
    // @Positive(message = "Requested point amount must be positive") // 0보다 큰지 검사
    // private Long requestedPointAmount;

    @NotBlank(message = "Payment method ID cannot be blank")
    private String paymentMethodId;

    @NotNull(message = "Actual payment amount cannot be null")
    @Positive(message = "Actual payment amount must be positive")
    private BigDecimal actualPaymentAmount;

    // DTO는 일반적으로 비즈니스 로직을 포함하지 않고 데이터 전달 역할만 수행합니다.
}