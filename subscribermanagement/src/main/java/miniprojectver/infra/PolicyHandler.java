package miniprojectver.infra;
import java.util.Map;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    SubscribeManagementRepository subscribeManagementRepository;

    @Autowired
    ReadingManagementRepository readingManagementRepository;

    @Autowired
    PointRequestManagementRepository pointRequestManagementRepository;

    @Autowired
    BookPurchaseManagementRepository bookPurchaseManagementRepository;

    private final ObjectMapper mapper = new ObjectMapper();


    @StreamListener(KafkaProcessor.INPUT)
    public void handle(@Payload String payload) throws Exception {

    ObjectMapper mapper = new ObjectMapper();
    Map<?, ?> json = mapper.readValue(payload, Map.class);

    String type = (String) json.get("eventType");

    if (!"BookPurchaseRequested".equals(type)) return;

    BookPurchaseRequested e = mapper.readValue(payload, BookPurchaseRequested.class);
    String userId = e.getUserId();

    // ✨ 구독 여부 조회 (가짜 로직 또는 외부 API 호출)
    boolean isSubscribed = false;
    try {
        // 예시 로직: 유저가 user001이면 구독 중으로 처리
        isSubscribed = "user001".equals(userId);
    } catch (Exception ex) {
        System.out.println("❗ 구독 조회 실패: " + ex.getMessage());
    }

    // 📩 SubscriptionStatusChecked 이벤트 발행
    SubscriptionStatusChecked event = new SubscriptionStatusChecked();
    event.setUserId(userId);
    event.setBookId(e.getBookId());
    event.setPrice(e.getPrice());
    event.setIsSubscribed(isSubscribed);

    event.publish();  // 또는 processor.output().send(event.toMessage()) 방식으로도 가능

    System.out.println("📩 SubscriptionStatusChecked 발행 완료: userId=" + userId + ", isSubscribed=" + isSubscribed);
}

    
}
//>>> Clean Arch / Inbound Adaptor
