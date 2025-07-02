package miniprojectver.message;

import lombok.Data;

@Data
public class MemberJoined {
    private String userId;
    private Boolean isktCustomer;

    public MemberJoined() {}

    public MemberJoined(String userId, Boolean isktCustomer) {
        this.userId = userId;
        this.isktCustomer = isktCustomer;
    }
}
