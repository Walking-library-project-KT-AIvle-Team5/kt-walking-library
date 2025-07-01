package miniprojectver.domain;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bestseller_read_model")
@Getter @Setter @NoArgsConstructor
public class BestsellerReadModel {

    @Id
    private String bookId;

    private String authorId;
    private int totalPurchases;
    private LocalDateTime registeredAt;
}