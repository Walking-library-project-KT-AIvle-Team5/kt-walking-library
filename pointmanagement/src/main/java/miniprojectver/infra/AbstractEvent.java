package miniprojectver.infra;

import lombok.*;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@Getter @Setter
public abstract class AbstractEvent {

    private String eventId = java.util.UUID.randomUUID().toString();

    public String getEventType() {
        return this.getClass().getSimpleName();
    }

    public Message<String> toMessage() throws Exception {
        String payload = new ObjectMapper().writeValueAsString(this);
        return MessageBuilder.withPayload(payload)
            .setHeader("type", getEventType())   // 이벤트 타입 자동 추가
            .build();
    }
}
