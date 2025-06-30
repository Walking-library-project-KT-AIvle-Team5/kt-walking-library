package miniprojectver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum YesNo {
    YES("Y", true),
    NO("N", false);

    private final String dbValue;
    private final boolean booleanValue;

    YesNo(String dbValue, boolean booleanValue) {
        this.dbValue = dbValue;
        this.booleanValue = booleanValue;
    }

    // 이 어노테이션이 핵심입니다. JSON의 문자열을 Enum으로 변환하는 방법을 정의합니다.
    @JsonCreator
    public static YesNo fromString(String value) {
        if (value == null) {
            return null;
        }
        String upperValue = value.toUpperCase();
        if ("YES".equals(upperValue) || "Y".equals(upperValue)) {
            return YES;
        }
        if ("NO".equals(upperValue) || "N".equals(upperValue)) {
            return NO;
        }
        // 원한다면 "true", "false"도 처리 가능
        if ("TRUE".equals(upperValue)) return YES;
        if ("FALSE".equals(upperValue)) return NO;

        return null; // 혹은 예외 발생
    }
}

