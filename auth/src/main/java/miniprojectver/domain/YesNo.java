package miniprojectver.domain;

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

    public String getDbValue() {
        return this.dbValue;
    }
}

