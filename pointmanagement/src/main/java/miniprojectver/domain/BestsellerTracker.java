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

    public void increment() {
        this.purchaseCount++;
    }

    public void markAsBestseller() {
        this.bestseller = true;
    }
}
