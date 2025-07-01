package miniprojectver.domain;

import lombok.Data;

@Data
public class BestSellerRegistered {
    private String bookId;
    private String authorId;
    private Integer totalPurchases;
}
