package miniprojectver.dto;

import lombok.Data; // @Getter, @Setter 등을 자동으로 만들어 줍니다.

@Data
public class UpdateProgressCommand {
    private Integer currentPage; // 업데이트할 페이지를 담을 필드
}