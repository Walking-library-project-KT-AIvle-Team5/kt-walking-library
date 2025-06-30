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
import miniprojectver.domain.BookPurchaseRequested;

@Entity
@Table(name = "BookPurchaseManagement_table")
@Data
//<<< DDD / Aggregate Root
public class BookPurchaseManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long purchaseRequestId;

    private String userId;

    private String bookId;

    private BigDecimal price;

    private BigDecimal point;

    @PostPersist
    public void onPostPersist() {
        BookPurchaseRequested bookPurchaseRequested = new BookPurchaseRequested(
            this
        );
        bookPurchaseRequested.publishAfterCommit();
    }

    public static BookPurchaseManagementRepository repository() {
        BookPurchaseManagementRepository bookPurchaseManagementRepository = SubscribermanagementApplication.applicationContext.getBean(
            BookPurchaseManagementRepository.class
        );
        return bookPurchaseManagementRepository;
    }
}
//>>> DDD / Aggregate Root
