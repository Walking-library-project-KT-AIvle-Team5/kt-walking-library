package miniprojectver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniprojectver.SubscribermanagementApplication;
import miniprojectver.domain.ProgressUpdated;
import miniprojectver.domain.ReadingCompleted;
import miniprojectver.domain.ReadingStarted;

@Entity
@Table(name = "ReadingManagement_table")
@Data
//<<< DDD / Aggregate Root
public class ReadingManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long readingActivityId;

    private String userId;

    private String bookId;

    private Integer currentPage;

    private String readingStatus;

    private Date startedAt;

    private Date completedAt;

    private Date lastUpdatedAt;

    @PostPersist
    public void onPostPersist() {
        ReadingStarted readingStarted = new ReadingStarted(this);
        readingStarted.publishAfterCommit();

        ProgressUpdated progressUpdated = new ProgressUpdated(this);
        progressUpdated.publishAfterCommit();

        ReadingCompleted readingCompleted = new ReadingCompleted(this);
        readingCompleted.publishAfterCommit();
    }

    public static ReadingManagementRepository repository() {
        ReadingManagementRepository readingManagementRepository = SubscribermanagementApplication.applicationContext.getBean(
            ReadingManagementRepository.class
        );
        return readingManagementRepository;
    }
}
//>>> DDD / Aggregate Root
