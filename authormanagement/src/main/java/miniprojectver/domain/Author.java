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
import miniprojectver.domain.RegistrationDenied;

@Entity
@Table(name = "Author_table")
@Data
//<<< DDD / Aggregate Root
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    private Long memberId;

    private String authorName;

    private String authorInfo;

    private String portfolio;
    
    private String authorRole;

    @Temporal(TemporalType.TIMESTAMP)
    private Date requestedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date checkedDate;

    private String denialReason;

    @PrePersist
    public void onprePersist() {
        if (this.requestedDate == null) {
            this.requestedDate = new Date();
        }
    }

    @PostPersist
    public void onPostPersist() {
        AuthorRegistrationRequested authorRegistrationRequested = new AuthorRegistrationRequested(
            this
        );
        authorRegistrationRequested.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.checkedDate = new Date();
    if ("APPROVED".equals(this.authorRole)) {
        RegistrationApproved registrationApproved = new RegistrationApproved(this);
        registrationApproved.publishAfterCommit();
    } else if ("DENIED".equals(this.authorRole)) {
        RegistrationDenied registrationDenied = new RegistrationDenied(this);
        registrationDenied.publishAfterCommit();
    }
}

    @PreRemove
    public void onPreRemove() {
        AuthorDeleted authorDeleted = new AuthorDeleted(this);
        authorDeleted.publishAfterCommit();
    }

    public static AuthorRepository repository() {
        AuthorRepository authorRepository = AuthormanagementApplication.applicationContext.getBean(
            AuthorRepository.class
        );
        return authorRepository;
    }
}
//>>> DDD / Aggregate Root


