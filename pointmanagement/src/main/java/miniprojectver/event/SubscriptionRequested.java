package miniprojectver.message;

import lombok.Data;

@Data
public class SubscriptionRequested {
    private String userId;

    public SubscriptionRequested() {}

    public SubscriptionRequested(String userId) {
        this.userId = userId;
    }
}
