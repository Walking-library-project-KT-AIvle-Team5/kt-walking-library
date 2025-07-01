package miniprojectver.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class YesNoConverter implements AttributeConverter<YesNo, String> {

    @Override
    public String convertToDatabaseColumn(YesNo attribute) {
        if (attribute == null) return null;
        return attribute.getDbValue();
    }

    @Override
    public YesNo convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        if ("Y".equalsIgnoreCase(dbData)) return YesNo.YES;
        if ("N".equalsIgnoreCase(dbData)) return YesNo.NO;
        return null;
    }
}

