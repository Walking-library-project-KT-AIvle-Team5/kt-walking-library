package miniprojectver.domain;

import lombok.Data;
import miniprojectver.infra.AbstractEvent;

@Data
public class BookPublicationChecked extends AbstractEvent {
    private Long bookId;
    private String bookName;
    private Long authorId;
    private String authorName;
    private String category;
    private String summary;
    private String imagePath;
    private String contents;
    private Integer price;
    private String status;

    // ✅ Book 객체를 받아 필드 세팅하는 생성자 추가
    public BookPublicationChecked(Book book) {
        super(book);
        this.bookId = book.getBookId();
        this.bookName = book.getBookName();
        this.authorId = book.getAuthorId();
        this.authorName = book.getAuthorName();
        this.category = book.getCategory();
        this.summary = book.getSummary();
        this.imagePath = book.getImagePath();
        this.contents = book.getContents();
        this.price = book.getPrice();
        this.status = (book.getStatus() != null) ? book.getStatus().name() : null;
    }

    public BookPublicationChecked() {
        super();
    }
}
