package miniprojectver.infra;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;

@Getter
public abstract class AbstractEvent {

    private final String eventType = getClass().getSimpleName();
    private final String timestamp = LocalDateTime.now().toString();

    @JsonIgnore
    public Message<String> toMessage(){
        try{
            String json = new ObjectMapper().writeValueAsString(this);
            return MessageBuilder.withPayload(json)
                    .setHeader("eventType", eventType).build();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
