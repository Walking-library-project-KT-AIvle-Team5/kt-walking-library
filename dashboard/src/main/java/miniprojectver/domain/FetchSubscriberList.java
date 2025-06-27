package miniprojectver.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "FetchSubscriberList_table")
@Data
public class FetchSubscriberList {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long subscriptionId;
    private String userId;
    private Date startedAt;
    private Date endAt;
    private String status;
}
