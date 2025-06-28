package miniprojectver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniprojectver.AuthormanagementApplication;
import miniprojectver.domain.AuthorDeleted;
import miniprojectver.domain.AuthorRegistrationRequested;
import miniprojectver.domain.RegistrationApproved;

@Entity
@Table(name = "AuthorRegistration_table")
@Data
//<<< DDD / Aggregate Root
public class AuthorRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long authorId;

    private String authorName;

    private String authorInfo;

    private String authorPortfolio;

    private Boolean authorRole;

    private Date craeteDate;

    @PostPersist
    public void onPostPersist() {
        AuthorRegistrationRequested authorRegistrationRequested = new AuthorRegistrationRequested(
            this
        );
        authorRegistrationRequested.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        RegistrationApproved registrationApproved = new RegistrationApproved(
            this
        );
        registrationApproved.publishAfterCommit();
    }

    @PostRemove
    public void onPostRemove() {
        AuthorDeleted authorDeleted = new AuthorDeleted(this);
        authorDeleted.publishAfterCommit();
    }

    public static AuthorRegistrationRepository repository() {
        AuthorRegistrationRepository authorRegistrationRepository = AuthormanagementApplication.applicationContext.getBean(
            AuthorRegistrationRepository.class
        );
        return authorRegistrationRepository;
    }
}
//>>> DDD / Aggregate Root
