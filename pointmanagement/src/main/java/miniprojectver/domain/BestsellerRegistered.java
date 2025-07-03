package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

@Getter @Setter
@NoArgsConstructor
public class BestsellerRegistered extends AbstractEvent {
    private String bookId;

    public BestsellerRegistered(String bookId) {
        super();
        this.bookId = bookId;
    }
}
