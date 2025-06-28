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
import miniprojectver.domain.PaymentRecommendationMessageSent;
import miniprojectver.domain.SubscriptionStatusChecked;

@Entity
@Table(name = "PlatformManagement_table")
@Data
//<<< DDD / Aggregate Root
public class PlatformManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subscribedUserIds;

    private String recommendationMessage;

    public static PlatformManagementRepository repository() {
        PlatformManagementRepository platformManagementRepository = LibraryplatformApplication.applicationContext.getBean(
            PlatformManagementRepository.class
        );
        return platformManagementRepository;
    }

    //<<< Clean Arch / Port Method
    public static void checkSubscription(
        BookPurchaseRequested bookPurchaseRequested
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        PlatformManagement platformManagement = new PlatformManagement();
        repository().save(platformManagement);

        SubscriptionStatusChecked subscriptionStatusChecked = new SubscriptionStatusChecked(platformManagement);
        subscriptionStatusChecked.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(bookPurchaseRequested.get???()).ifPresent(platformManagement->{
            
            platformManagement // do something
            repository().save(platformManagement);

            SubscriptionStatusChecked subscriptionStatusChecked = new SubscriptionStatusChecked(platformManagement);
            subscriptionStatusChecked.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
