package miniprojectver.command;

import java.util.*;
import lombok.*;

/*[Command] KT 고객 보너스 포인트 5,000 지급
 */
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class GrantBonusPointCommand {

    private String userId;

    /** 지급 포인트(보너스) – 기본값 5,000 */
    @Builder.Default
    private Integer amount = 5000;

    private String correlationId;
}
