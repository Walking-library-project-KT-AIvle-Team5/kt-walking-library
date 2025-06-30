package miniprojectver.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

// 이 컨버터를 자동으로 모든 YesNo 타입에 적용합니다.
@Converter(autoApply = true)
public class YesNoConverter implements AttributeConverter<YesNo, String> {

    // YesNo Enum을 DB에 저장할 'Y' 또는 'N' 문자열로 변환
    @Override
    public String convertToDatabaseColumn(YesNo attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDbValue();
    }

    // DB의 'Y' 또는 'N' 문자열을 YesNo Enum으로 변환
    @Override
    public YesNo convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        if ("Y".equalsIgnoreCase(dbData)) {
            return YesNo.YES;
        }
        if ("N".equalsIgnoreCase(dbData)) {
            return YesNo.NO;
        }
        return null; // 혹은 예외 발생
    }
}

