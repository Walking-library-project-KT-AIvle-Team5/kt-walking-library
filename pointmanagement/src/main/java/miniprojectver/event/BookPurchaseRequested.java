package miniprojectver.message;

import lombok.Data;

@Data
public class BookPurchaseRequested {
    private String userId;
    private String bookId;
    private String authorId;
    private Integer amount;

    public BookPurchaseRequested() {}

    public BookPurchaseRequested(String userId, String bookId, String authorId, Integer amount) {
        this.userId = userId;
        this.bookId = bookId;
        this.authorId = authorId;
        this.amount = amount;
    }
}
