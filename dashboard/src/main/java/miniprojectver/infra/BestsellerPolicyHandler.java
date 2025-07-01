package miniprojectver.infra;

import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// ✅ 이벤트 발행용 AbstractEvent
import miniprojectver.infra.AbstractEvent;

@Service
@Transactional
@RequiredArgsConstructor
public class BestsellerPolicyHandler {

    private static final Logger log = LoggerFactory.getLogger(BestsellerPolicyHandler.class);

    private final BestsellerCountRepository countRepo;
    private final BestsellerReadModelRepository readRepo;

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointDeducted'"
    )
    public void onPointDeducted(@Payload PointDeducted evt) {

        log.info("📥 PointDeducted 이벤트 수신됨: {}", evt);

        if (evt.getBookId() == null) {
            log.warn("⚠️ bookId가 null입니다. 이벤트 무시됨.");
            return;
        }

        BestsellerCount cnt =
            countRepo.findById(evt.getBookId())
                     .orElseGet(() -> {
                         log.info("📘 신규 책 구매 기록 생성: {}", evt.getBookId());
                         BestsellerCount n = new BestsellerCount();
                         n.setBookId(evt.getBookId());
                         n.setAuthorId(evt.getAuthorId());
                         n.setPurchaseCount(0);
                         return n;
                     });

        cnt.setPurchaseCount(cnt.getPurchaseCount() + 1);
        countRepo.save(cnt);
        log.info("📊 누적 구매 횟수 [{}]: {}", evt.getBookId(), cnt.getPurchaseCount());

        if (cnt.getPurchaseCount() == 5) {
            log.info("🎉 베스트셀러 조건 충족! 등록 시작: {}", cnt.getBookId());

            BestsellerReadModel bm = new BestsellerReadModel();
            bm.setBookId(cnt.getBookId());
            bm.setAuthorId(cnt.getAuthorId());
            bm.setTotalPurchases(cnt.getPurchaseCount());
            bm.setRegisteredAt(java.time.LocalDateTime.now());
            readRepo.save(bm);

            log.info("✅ 리드모델 저장 완료: {}", bm);

            BestSellerRegistered out = new BestSellerRegistered();
            out.setBookId(cnt.getBookId());
            out.setAuthorId(cnt.getAuthorId());
            out.setTotalPurchases(cnt.getPurchaseCount());

            log.info("📤 BestSellerRegistered 이벤트 발행 준비: {}", out);
            new AbstractEvent(out).publishAfterCommit();
        }
    }
}
