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
    private String summary ="";
    private String category;
    private String imagepath = "";
    private String contents;

    @Enumerated(EnumType.STRING)
    private BookStatus status; // "PENDING", "APPROVED", "REJECTED", "UNPUBLISHED"

    private Integer price;

    @PostUpdate
    public void onPostUpdate() {
        if (BookStatus.ARCHIVED.equals(this.status)) {
            BookUnpublished event = new BookUnpublished(this);
            event.publishAfterCommit();
        }

        if (BookStatus.PUBLISHED.equals(this.status)) {
            BookPublicationChecked event = new BookPublicationChecked(this);
            event.publishAfterCommit();
        }
    }

    public static BookPublicationRepository repository() {
        return LibraryplatformApplication.applicationContext.getBean(BookPublicationRepository.class);
    }

    //<<< Clean Arch / Port Method
    public static void requestBookPublication(ManuscriptPublicationRequested event) {
        BookPublication bookPublication = new BookPublication();
        bookPublication.setTitle(event.getTitle());
        bookPublication.setSummaryContent(event.getSummaryContent());
        bookPublication.setSummary(event.getSummary());
        bookPublication.setContents(event.getContent());
        bookPublication.setCategory(event.getCategory());
        bookPublication.setImagepath(event.getImagePath());
        bookPublication.setPrice(event.getPrice());

        // ManuscriptStatus → BookStatus 매핑
        ManuscriptStatus manuscriptStatus = event.getStatus();
            if (manuscriptStatus == ManuscriptStatus.DRAFT || manuscriptStatus == ManuscriptStatus.EDITED) {
                bookPublication.setStatus(BookStatus.DRAFT);
            } else if (manuscriptStatus == ManuscriptStatus.PUBLISH_REQUESTED) {
                bookPublication.setStatus(BookStatus.DRAFT);
            } else if (manuscriptStatus == ManuscriptStatus.PUBLISHED) {
                bookPublication.setStatus(BookStatus.PUBLISHED);
            } else {
                bookPublication.setStatus(BookStatus.DRAFT);
            }

        repository().save(bookPublication);
    }
    //>>> Clean Arch / Port Method
}
