package miniprojectver.message;

import lombok.Data;

@Data
public class PointPurchaseRequested {
    private String userId;
    private Integer amount;

    public PointPurchaseRequested() {}

    public PointPurchaseRequested(String userId, Integer amount) {
        this.userId = userId;
        this.amount = amount;
    }
}
