package miniprojectver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miniprojectver.infra.AbstractEvent;

/**
 * “회원가입 완료” 이벤트 스펙 (포인트 서비스용)
 * 1000P 기본 · KT 고객이면 +5 000P 추가
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // 불필요한 필드 무시
public class MemberSignedUp extends AbstractEvent {

    /** auth-service 가 보내주는 loginId 를 그대로 userId 로 사용 */
    private String userId;

    /** 내부 필드는 boolean으로 유지 */
    private boolean isKtCustomer;

    /**
     * JSON에서 문자열("YES", "true") 또는 boolean을 허용하는 커스텀 매핑
     */
    @JsonProperty("isKtCustomer")
    private void unpackIsKtCustomer(Object value) {
        if (value instanceof Boolean) {
            this.isKtCustomer = (Boolean) value;
        } else if (value instanceof String) {
            this.isKtCustomer = "YES".equalsIgnoreCase((String) value)
                             || "true".equalsIgnoreCase((String) value);
        }
    }
}
