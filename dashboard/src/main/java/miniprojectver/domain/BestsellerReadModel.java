package miniprojectver.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BestsellerReadModel {

    @Id
    private Long bookId;

    private String title;     // 선택: 다른 서비스에서 book title 정보도 함께 보내줄 경우
    private Long totalPurchases; // 기본값: 5로 등록 시작
}
