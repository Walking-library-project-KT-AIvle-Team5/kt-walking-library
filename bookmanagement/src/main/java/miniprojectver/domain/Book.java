package miniprojectver.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import miniprojectver.BookmanagementApplication;

@Entity
@Table(name = "Book_table")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private String bookName;
    private Long authorId;
    private String authorName;
    private String category;
    private String summary;
    private String imagePath;
    private String contents;
    private Date publishedAt;
    private Boolean bestseller = false;
    private Status status;
    private Integer pointUsageCount = 0;
    private Integer price;  // ✅ price 필드 추가

    public static BookRepository repository() {
        return BookmanagementApplication.applicationContext.getBean(BookRepository.class);
    }

    public static void registerBook(BookPublicationChecked event) {
        Book book = new Book();
        book.bookName = event.getBookName();
        book.authorId = event.getAuthorId();
        book.authorName = event.getAuthorName();
        book.category = event.getCategory();
        book.summary = event.getSummary();
        book.imagePath = event.getImagePath();
        book.contents = event.getContents();
        book.price = event.getPrice();  // ✅ price 값 세팅
        book.publishedAt = new Date();
        book.bestseller = false;
        book.status = Status.REGISTERED;
        book.pointUsageCount = 0;

        repository().save(book);

        BookRegistered bookRegistered = new BookRegistered(book);
        bookRegistered.publishAfterCommit();
    }

    public static void markAsBestseller(PointDeducted event) {
        repository().findById(event.getBookId()).ifPresent(book -> {
            book.addPointUsage();
            repository().save(book);
        });
    }

    public void addPointUsage() {
        if (this.pointUsageCount == null) {
            this.pointUsageCount = 0;
        }
        this.pointUsageCount += 1;

        if (this.pointUsageCount >= 5 && (this.bestseller == null || !this.bestseller)) {
            this.bestseller = true;
            MarkedAsBestseller event = new MarkedAsBestseller(this);
            event.publishAfterCommit();
        }
    }
}
