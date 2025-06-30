// miniprojectver.infra.StartReadingCommand.java (예시)
package miniprojectver.dto; // 또는 다른 적절한 패키지

import lombok.Data; // @Getter, @Setter 등을 편리하게 사용하기 위함

@Data // 이 어노테이션이 getter, setter 등을 자동으로 만들어 줍니다.
public class StartReadingCommand {
    private String userId;
    private String bookId;
    private Integer initialPage;
}