package miniprojectver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniprojectver.BookmanagementApplication;
import miniprojectver.domain.BookRegistered;
import miniprojectver.domain.MarkedAsBestseller;

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

    private Status status;

    public static BookRepository repository() {
        BookRepository bookRepository = BookmanagementApplication.applicationContext.getBean(
            BookRepository.class
        );
        return bookRepository;
    }

    //<<< Clean Arch / Port Method
    public static void registerBook(
        BookPublicationChecked bookPublicationChecked
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Book book = new Book();
        repository().save(book);

        BookRegistered bookRegistered = new BookRegistered(book);
        bookRegistered.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(bookPublicationChecked.get???()).ifPresent(book->{
            
            book // do something
            repository().save(book);

            BookRegistered bookRegistered = new BookRegistered(book);
            bookRegistered.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void markAsBestseller(PointDeducted pointDeducted) {
        //implement business logic here:

        /** Example 1:  new item 
        Book book = new Book();
        repository().save(book);

        MarkedAsBestseller markedAsBestseller = new MarkedAsBestseller(book);
        markedAsBestseller.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(pointDeducted.get???()).ifPresent(book->{
            
            book // do something
            repository().save(book);

            MarkedAsBestseller markedAsBestseller = new MarkedAsBestseller(book);
            markedAsBestseller.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
