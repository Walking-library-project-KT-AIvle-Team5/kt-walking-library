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
import miniprojectver.domain.SubscriptionCancelled;
import miniprojectver.domain.SubscriptionRequested;

@Entity
@Table(name = "SubscribeManagement_table")
@Data
//<<< DDD / Aggregate Root
public class SubscribeManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscriptionId;

    private String userId;

    private String status;

    private Date startedAt;

    private Date endsAt;

    private Date lastRenewalAt;

    @PostPersist
    public void onPostPersist() {
        SubscriptionRequested subscriptionRequested = new SubscriptionRequested(
            this
        );
        subscriptionRequested.publishAfterCommit();

        SubscriptionCancelled subscriptionCancelled = new SubscriptionCancelled(
            this
        );
        subscriptionCancelled.publishAfterCommit();
    }

    public static SubscribeManagementRepository repository() {
        SubscribeManagementRepository subscribeManagementRepository = SubscribermanagementApplication.applicationContext.getBean(
            SubscribeManagementRepository.class
        );
        return subscribeManagementRepository;
    }
}
//>>> DDD / Aggregate Root
