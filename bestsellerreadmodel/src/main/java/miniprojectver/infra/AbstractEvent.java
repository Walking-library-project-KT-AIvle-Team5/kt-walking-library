package miniprojectver.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import miniprojectver.config.kafka.KafkaProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;

@Slf4j
public abstract class AbstractEvent {

    private static ApplicationContext applicationContext;

    private String eventType;
    private Date timestamp;

    public AbstractEvent() {
        this.timestamp = new Date();
        this.eventType = this.getClass().getSimpleName();
    }

    public void publish() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(this);

            KafkaProcessor processor = applicationContext.getBean(KafkaProcessor.class);
            MessageChannel outputChannel = processor.output();

            outputChannel.send(
                MessageBuilder.withPayload(json)
                    .setHeader("type", getEventType())
                    .build()
            );

            log.info("✅ Published event: {} / payload: {}", getEventType(), json);
        } catch (Exception e) {
            log.error("❌ Failed to publish event", e);
        }
    }

    public String getEventType() {
        return eventType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    // ✅ Setter 메서드 추가
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
}
