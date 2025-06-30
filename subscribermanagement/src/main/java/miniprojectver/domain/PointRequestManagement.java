package miniprojectver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import miniprojectver.SubscribermanagementApplication;
import miniprojectver.domain.PointPurchaseRequested;

@Entity
@Table(name = "PointRequestManagement_table")
@Data
//<<< DDD / Aggregate Root
public class PointRequestManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pointRequestId;

    private String userId;

    private Long requestedPointAmount;

    private String paymentMethodId;

    private BigDecimal actualPaymentAmount;

    private String status;

    private Date timestamp;

    @PostPersist
    public void onPostPersist() {
        PointPurchaseRequested pointPurchaseRequested = new PointPurchaseRequested(
            this
        );
        pointPurchaseRequested.publishAfterCommit();
    }

    public static PointRequestManagementRepository repository() {
        PointRequestManagementRepository pointRequestManagementRepository = SubscribermanagementApplication.applicationContext.getBean(
            PointRequestManagementRepository.class
        );
        return pointRequestManagementRepository;
    }
}
//>>> DDD / Aggregate Root
