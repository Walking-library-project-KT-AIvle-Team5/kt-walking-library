package miniprojectver.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "bestseller_count")
@Getter @Setter @NoArgsConstructor
public class BestsellerCount {

    @Id
    private String bookId;

    private String authorId;
    private int purchaseCount;
}