package miniprojectver.command;

import java.util.*;
import lombok.*;

/* [Command] 회원 가입 시 기본 포인트 1,000 지급
 */
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class GrantBasicPointCommand {

    private String userId;

    /** 지급 포인트(기본값 1000) – 기본값을 컨트롤러/서비스에서 세팅해도 됨 */
    @Builder.Default
    private Integer amount = 1000;

    private String correlationId;
}
