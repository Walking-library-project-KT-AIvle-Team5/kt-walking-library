package miniprojectver.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.*;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j                 // ← Lombok 로그
@Service
@Transactional
@RequiredArgsConstructor
public class BestsellerPolicyHandler {

    private final BestsellerCountRepository countRepo;
    private final BestsellerReadModelRepository readRepo;

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointDeducted'"
    )
    public void onPointDeducted(@Payload PointDeducted evt) {

        log.info("📥 PointDeducted 수신: {}", evt);

        /* 1) bookId 체크 */
        if (evt.getBookId() == null) {
            log.warn("⚠️ bookId null → 이벤트 무시");
            return;
        }

        /* 2) 누적 카운트 증가 */
        BestsellerCount cnt = countRepo.findById(evt.getBookId())
                                       .orElseGet(() -> {
                                           log.info("🔖 첫 구매, 카운트 row 생성");
                                           BestsellerCount n = new BestsellerCount();
                                           n.setBookId(evt.getBookId());
                                           n.setAuthorId(evt.getAuthorId());
                                           n.setPurchaseCount(0);
                                           return n;
                                       });

        cnt.setPurchaseCount(cnt.getPurchaseCount() + 1);
        countRepo.save(cnt);
        log.info("📊 누적[{}] → {}", cnt.getBookId(), cnt.getPurchaseCount());

        /* 3) 5회 이상 & 아직 리드모델 미존재면 베스트셀러 등록 */
        if (cnt.getPurchaseCount() >= 5 &&
            readRepo.findById(cnt.getBookId()).isEmpty()) {

            log.info("🎉 베스트셀러 등재! {}", cnt.getBookId());

            BestsellerReadModel bm = new BestsellerReadModel();
            bm.setBookId(cnt.getBookId());
            bm.setAuthorId(cnt.getAuthorId());
            bm.setTotalPurchases(cnt.getPurchaseCount());
            bm.setRegisteredAt(java.time.LocalDateTime.now());
            readRepo.save(bm);

            /* 4) 외부 전파 이벤트 */
            BestSellerRegistered out = new BestSellerRegistered();
            out.setBookId(bm.getBookId());
            out.setAuthorId(bm.getAuthorId());
            out.setTotalPurchases(bm.getTotalPurchases());

            new AbstractEvent(out).publishAfterCommit();
        }
    }
}
