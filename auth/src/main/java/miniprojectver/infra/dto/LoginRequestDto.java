package miniprojectver.infra.dto;

public class LoginRequestDto {
    private String loginId;
    private String password;

    // --- Getters ---
    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
}
