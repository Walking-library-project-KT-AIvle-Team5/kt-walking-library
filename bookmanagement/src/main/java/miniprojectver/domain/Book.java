package miniprojectver.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import miniprojectver.BookmanagementApplication;

@Entity
@Table(name = "Book_table")
@Data
//<<< DDD / Aggregate Root
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

    private Boolean isBestseller;

    private BookStatus status;

    private Integer price;  // ✅ price 필드 추가

    public static BookRepository repository() {
        return BookmanagementApplication.applicationContext.getBean(BookRepository.class);
    }

    //<<< Clean Arch / Port Method
    public static void registerBook(BookPublicationChecked bookPublicationChecked) {
        Book book = new Book();

        book.setBookId(bookPublicationChecked.getId());
        book.setBookName(bookPublicationChecked.getTitle());
        book.setCategory(bookPublicationChecked.getCategory());
        book.setSummary(bookPublicationChecked.getSummary());
        book.setImagePath(bookPublicationChecked.getImagepath());
        book.setContents(bookPublicationChecked.getContents());
        book.setStatus(BookStatus.valueOf(bookPublicationChecked.getStatus()));
        book.setPrice(bookPublicationChecked.getPrice());
        book.setPublishedAt(new Date());
        book.setIsBestseller(false);

        repository().save(book);

        BookRegistered bookRegistered = new BookRegistered(book);
        bookRegistered.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

    public static void markAsBestseller(PointDeducted pointDeducted) {
        // 필요 시 작성
    }
}
//>>> DDD / Aggregate Root