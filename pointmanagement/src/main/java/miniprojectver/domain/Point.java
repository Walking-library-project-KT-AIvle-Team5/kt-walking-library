package miniprojectver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniprojectver.PointmanagementApplication;
import miniprojectver.domain.BasicPointGranted;
import miniprojectver.domain.BonusPointGranted;
import miniprojectver.domain.PointChargeFailed;
import miniprojectver.domain.PointCharged;
import miniprojectver.domain.PointDeducted;
import miniprojectver.domain.PointUseFailed;

@Entity
@Table(name = "Point_table")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pointAccountId;

    private String userId;

    private Integer currentPoint;

    private Long totalCharged;

    private Long totalUsed;

    private Boolean isktCustomer;

    public static PointRepository repository() {
        PointRepository pointRepository = PointmanagementApplication.applicationContext.getBean(
            PointRepository.class
        );
        return pointRepository;
    }

    //<<< Clean Arch / Port Method
    public static void tryPointDeduction(
        SubscriptionStatusChecked subscriptionStatusChecked
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        PointDeducted pointDeducted = new PointDeducted(point);
        pointDeducted.publishAfterCommit();
        PointUseFailed pointUseFailed = new PointUseFailed(point);
        pointUseFailed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(subscriptionStatusChecked.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);

            PointDeducted pointDeducted = new PointDeducted(point);
            pointDeducted.publishAfterCommit();
            PointUseFailed pointUseFailed = new PointUseFailed(point);
            pointUseFailed.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void tryPointDeduction(
        SubscriptionRequested subscriptionRequested
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        PointDeducted pointDeducted = new PointDeducted(point);
        pointDeducted.publishAfterCommit();
        PointUseFailed pointUseFailed = new PointUseFailed(point);
        pointUseFailed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(subscriptionRequested.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);

            PointDeducted pointDeducted = new PointDeducted(point);
            pointDeducted.publishAfterCommit();
            PointUseFailed pointUseFailed = new PointUseFailed(point);
            pointUseFailed.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void grantBasicPoint(MemberJoined memberJoined) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        BasicPointGranted basicPointGranted = new BasicPointGranted(point);
        basicPointGranted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(memberJoined.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);

            BasicPointGranted basicPointGranted = new BasicPointGranted(point);
            basicPointGranted.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
