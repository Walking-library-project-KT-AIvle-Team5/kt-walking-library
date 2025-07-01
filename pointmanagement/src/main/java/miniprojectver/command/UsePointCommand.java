package miniprojectver.command;

import java.util.*;
import lombok.*;

/* [Command] 포인트 사용(차감) 요청
 */
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class UsePointCommand {

    private String userId;

    /*책 ID – 개별 도서 구매 시 사용 */
    private String bookId;

    /** 차감할 포인트 (양수) */
    private Integer amount;

    private String authorId;

    private String correlationId;
}
