package miniprojectver.infra.dto;

// @Data 어노테이션을 제거하고, 필요한 Getter 메서드를 직접 추가합니다.
public class SignUpRequestDto {
    private String loginId;
    private String password;
    private String name;
    private String isKtCustomer;
    private String role;
    private String status;

    // --- Getters ---
    public String getLoginId() {
        return this.loginId;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public String getIsKtCustomer() {
        return this.isKtCustomer;
    }

    public String getRole() {
        return this.role;
    }

    public String getStatus() {
        return this.status;
    }
}