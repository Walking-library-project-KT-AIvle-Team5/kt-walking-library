package miniprojectver.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import miniprojectver.DashboardApplication;
import miniprojectver.config.kafka.KafkaProcessor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.MimeTypeUtils;

/**
 * 📤 Kafka 전송용 공통 이벤트 Wrapper
 *  - new AbstractEvent(dto).publishAfterCommit(); 으로 사용
 */
public class AbstractEvent {

    private String eventType;
    private Long timestamp;
    private Object payload;

    // ✅ 생성자: DTO 포함
    public AbstractEvent(Object payload) {
        this.payload = payload;
        this.eventType = payload.getClass().getSimpleName();
        this.timestamp = System.currentTimeMillis();
    }

    /** 즉시 발행 */
    public void publish() {
        KafkaProcessor processor = DashboardApplication
            .applicationContext
            .getBean(KafkaProcessor.class);

        MessageChannel outputChannel = processor.outboundTopic();

        outputChannel.send(
            MessageBuilder.withPayload(payload)
                          .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                          .setHeader("type", eventType)
                          .build()
        );
    }

    /** 트랜잭션 커밋 후 발행 */
    public void publishAfterCommit() {
        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronizationAdapter() {
                @Override
                public void afterCompletion(int status) {
                    AbstractEvent.this.publish();
                }
            }
        );
    }

    public String getEventType() { return eventType; }
    public Long getTimestamp() { return timestamp; }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validate() {
        return eventType != null && eventType.equals(payload.getClass().getSimpleName());
    }
}
