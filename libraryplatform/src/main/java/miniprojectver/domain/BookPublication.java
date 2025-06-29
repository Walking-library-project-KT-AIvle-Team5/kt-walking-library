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

    private String status; // "PENDING", "APPROVED", "REJECTED", "UNPUBLISHED"

    private Integer price;

    @PostUpdate
    public void onPostUpdate() {
        if ("UNPUBLISHED".equals(this.status)) {
            BookUnpublished event = new BookUnpublished(this);
            event.publishAfterCommit();
        }

        if ("APPROVED".equals(this.status)) {
            BookPublicationChecked event = new BookPublicationChecked(this);
            event.publishAfterCommit();
        }
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
        BookPublication bookPublication = new BookPublication();
        bookPublication.setTitle(manuscriptPublicationRequested.getTitle());
        bookPublication.setSummaryContent("요약내용 자동생성"); // or 필요시 API 호출 등
        bookPublication.setSummary(manuscriptPublicationRequested.getSummary());
        bookPublication.setContents(manuscriptPublicationRequested.getContent());
        bookPublication.setCategory(manuscriptPublicationRequested.getCategory());
        bookPublication.setImagepath(manuscriptPublicationRequested.getImagePath());
        bookPublication.setPrice(manuscriptPublicationRequested.getPrice());
        bookPublication.setStatus("PENDING");

        repository().save(bookPublication);

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
