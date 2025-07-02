package miniprojectver.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "bestseller_tracker")
public class BestsellerTracker {
    @Id
    private Long bookId;

    private Long purchaseCount;
    private Boolean bestseller;

    public void increment() {
        this.purchaseCount++;
        if (!this.bestseller && this.purchaseCount >= 5) {
            this.bestseller = true;
        }
    }
}
