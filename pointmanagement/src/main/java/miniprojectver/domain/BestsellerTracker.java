package miniprojectver.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class BestsellerTracker {

    @Id
    private String bookId;

    private Long purchaseCount = 0L;
    private Boolean bestseller = false;

    // ✅ 기본 1 증가
    public void increment() {
        this.purchaseCount++;
    }

    // ✅ N 증가
    public void incrementBy(long count) {
        this.purchaseCount += count;
    }

    // ✅ 베스트셀러 지정
    public void markAsBestseller() {
        this.bestseller = true;
    }
}
