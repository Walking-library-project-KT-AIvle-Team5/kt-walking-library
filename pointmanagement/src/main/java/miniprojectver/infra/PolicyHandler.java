package miniprojectver.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.*;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PolicyHandler {

    private final PointRepository pointRepo;
    private final BestsellerTrackerRepository bestsellerRepo;
    private final KafkaProcessor processor;
    private final ObjectMapper mapper = new ObjectMapper();

    @StreamListener(KafkaProcessor.INPUT)
    public void handle(@Payload String payload) throws Exception {

        log.info("🔥 수신된 Kafka payload: {}", payload);

        Map<?, ?> json = mapper.readValue(payload, Map.class);
        String type = (String) json.get("eventType");

        if ("SubscriptionStatusChecked".equals(type)) {
            SubscriptionStatusChecked s = mapper.readValue(payload, SubscriptionStatusChecked.class);

            String uid     = s.getUserId();
            String bookId  = s.getBookId();
            long   price   = s.getPrice().longValue();
            boolean subbed = Boolean.TRUE.equals(s.getIsSubscribed());

            Point point = pointRepo.findByUserId(uid)
                                   .orElse(Point.create(uid, false));

            long delta = subbed ? 0 : -price;   // 구독자면 0P, 아니면 차감
            point.apply(delta);
            pointRepo.save(point);

            // PointDeducted 이벤트 발행
            PointDeducted pd = new PointDeducted();
            pd.setUserId(uid);
            pd.setAmount(delta);        // 0 또는 –가격
            pd.setReason("BOOK_PURCHASE");
            processor.output().send(pd.toMessage());

            // 베스트셀러 집계
            handleBestsellerLogic(bookId, 1L);

            log.info("▶ BOOK PURCHASE 처리 완료 (userId={}, Δ{}P, 잔액={})",
                     uid, delta, point.getCurrentAmount());
            return;
        }
    }

    private void handleBestsellerLogic(String bookId, Long countIncrement) {
        if (bookId == null || countIncrement == null || countIncrement <= 0) return;

        BestsellerTracker tr = bestsellerRepo.findById(bookId)
                                             .orElse(new BestsellerTracker());

        tr.setBookId(bookId);
        tr.incrementBy(countIncrement);
        bestsellerRepo.save(tr);

        if (!tr.getBestseller() && tr.getPurchaseCount() >= 5) {
            tr.setBestseller(true);
            bestsellerRepo.save(tr);

            BestsellerRegistered ev = new BestsellerRegistered(bookId);
            processor.output().send(ev.toMessage());
            log.info("🌟 BestsellerRegistered 발행 bookId={}", bookId);
        }
    }
}
