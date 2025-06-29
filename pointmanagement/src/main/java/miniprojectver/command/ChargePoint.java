package miniprojectver.command;

import lombok.*;
import java.util.*;

/**
 * [Command] 포인트 충전 요청
 */
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class ChargePointCommand {

    /** 대상 회원 식별자 */
    private String userId;

    /** 충전 포인트 (양수) */
    private Integer amount;

    /** 트랜잭션·트레이싱용 Correlation ID */
    private String correlationId;
}
