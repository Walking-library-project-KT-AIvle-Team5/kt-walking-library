package miniprojectver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniprojectver.LibraryplatformApplication;
import miniprojectver.domain.BookPublicationChecked;
import miniprojectver.domain.BookUnpublished;

@Entity
@Table(name = "BookPublication_table")
@Data
//<<< DDD / Aggregate Root
public class BookPublication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String summaryContent;

    private String summary;

    private String category;

    private String imagepath;

    private String contents;

    private String status;

    private Integer price;

    @PostUpdate
    public void onPostUpdate() {
        BookUnpublished bookUnpublished = new BookUnpublished(this);
        bookUnpublished.publishAfterCommit();

        BookPublicationChecked bookPublicationChecked = new BookPublicationChecked(
            this
        );
        bookPublicationChecked.publishAfterCommit();
    }

    public static BookPublicationRepository repository() {
        BookPublicationRepository bookPublicationRepository = LibraryplatformApplication.applicationContext.getBean(
            BookPublicationRepository.class
        );
        return bookPublicationRepository;
    }

    //<<< Clean Arch / Port Method
    public static void requestBookPublication(
        ManuscriptPublicationRequested manuscriptPublicationRequested
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        BookPublication bookPublication = new BookPublication();
        repository().save(bookPublication);

        */

        /** Example 2:  finding and process
        

        repository().findById(manuscriptPublicationRequested.get???()).ifPresent(bookPublication->{
            
            bookPublication // do something
            repository().save(bookPublication);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
