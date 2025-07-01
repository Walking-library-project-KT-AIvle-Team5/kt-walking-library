package miniprojectver.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import miniprojectver.domain.YesNo;
import java.io.IOException;

public class YesNoDeserializer extends JsonDeserializer<YesNo> {

    @Override
    public YesNo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        if (value == null) {
            return null;
        }
        String upperValue = value.toUpperCase();
        if ("YES".equals(upperValue) || "Y".equals(upperValue) || "TRUE".equals(upperValue)) {
            return YesNo.YES;
        }
        if ("NO".equals(upperValue) || "N".equals(upperValue) || "FALSE".equals(upperValue)) {
            return YesNo.NO;
        }
        return null; // 혹은 예외 발생
    }
}