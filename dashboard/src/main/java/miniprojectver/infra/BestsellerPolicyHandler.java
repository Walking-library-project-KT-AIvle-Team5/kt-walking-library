package miniprojectver.infra;

import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.*;
import miniprojectver.event.BestSellerRegistered;
import miniprojectver.event.PointDeducted;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// ✅ 추가: 이벤트 발행용 AbstractEvent
import miniprojectver.infra.AbstractEvent;

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

        if (evt.getBookId() == null) return;

        BestsellerCount cnt =
            countRepo.findById(evt.getBookId())
                     .orElseGet(() -> {
                         BestsellerCount n = new BestsellerCount();
                         n.setBookId(evt.getBookId());
                         n.setAuthorId(evt.getAuthorId());
                         n.setPurchaseCount(0);
                         return n;
                     });

        cnt.setPurchaseCount(cnt.getPurchaseCount() + 1);
        countRepo.save(cnt);

        if (cnt.getPurchaseCount() == 5) {
            BestsellerReadModel bm = new BestsellerReadModel();
            bm.setBookId(cnt.getBookId());
            bm.setAuthorId(cnt.getAuthorId());
            bm.setTotalPurchases(cnt.getPurchaseCount());
            bm.setRegisteredAt(java.time.LocalDateTime.now());
            readRepo.save(bm);

            BestSellerRegistered out = new BestSellerRegistered();
            out.setBookId(cnt.getBookId());
            out.setAuthorId(cnt.getAuthorId());
            out.setTotalPurchases(cnt.getPurchaseCount());

            // ✅ 이벤트 발행 (payload 포함)
            new AbstractEvent(out).publishAfterCommit();
        }
    }
}
