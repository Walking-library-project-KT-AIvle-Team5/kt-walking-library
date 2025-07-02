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

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PolicyHandler {

    /* ─────────── DI ─────────── */
    private final PointRepository             pointRepo;
    private final BestsellerTrackerRepository bestsellerRepo;
    private final KafkaProcessor              processor;
    private final ObjectMapper                mapper = new ObjectMapper();

    /* ─────────── 메인 리스너 ─────────── */
    @StreamListener(KafkaProcessor.INPUT)
    public void handle(@Payload String payload) throws Exception {

        /* 🔥 RAW Kafka 메시지 확인용 임시 로그 */
        log.info("🔥 수신된 Kafka payload: {}", payload);

        Map<?, ?> json  = mapper.readValue(payload, Map.class);
        String    type  = (String) json.get("eventType");

        AbstractEvent event = (AbstractEvent) mapper.readValue(
                payload, PointAmountRegistry.typeToClass(type));

        /* ─── PointAmount 공통 분기 ─── */
        if (event instanceof PointAmount) {

            PointAmount pa  = (PointAmount) event;
            String      uid = extractUserId(event);
            BigDecimal  amt = pa.getAmount();               // +충전 / –차감 / 0

            Point point = pointRepo.findByUserId(uid)
                                   .orElse(Point.create(uid, false));

            /* ▼ 잔액 부족 시 PointUseFailed 발행 */
            if (amt.signum() < 0 &&
                point.getCurrentAmount() < Math.abs(amt.longValue())) {

                PointUseFailed failed = new PointUseFailed();
                failed.setUserId(uid);
                failed.setShortage(Math.abs(amt.longValue()) - point.getCurrentAmount());
                failed.setIsKtCustomer(point.getIsKtCustomer());

                processor.output().send(failed.toMessage());
                log.info("📩 PointUseFailed (userId={}, shortage={})",
                         uid, failed.getShortage());
                return;
            }

            /* ▼ 잔액 반영 */
            point.apply(amt.longValue());

            /* ▼ KT 고객 보너스 */
            if (event instanceof MemberJoined &&
                ((MemberJoined) event).isKtCustomer()) {

                point.apply(5_000L);
                log.info("KT 고객 보너스 +5,000P 지급 userId={}", uid);
            }

            pointRepo.save(point);

            /* ▼ 충전 완료 알림 */
            if (event instanceof PointPurchaseRequested) {
                processor.output().send(
                        new PointCharged(uid, amt.longValue()).toMessage());
                log.info("📩 PointCharged 발행 (userId={}, +{}P)", uid, amt);
            }

            /* ▼ 구독 신청 → 활성화 알림 */
            if (event instanceof SubscriptionRequested) {
                SubscriptionRequested sr = (SubscriptionRequested) event;

                SubscriptionActivated activated = new SubscriptionActivated();
                activated.setUserId(uid);
                activated.setStartedAt(sr.getStartedAt());
                activated.setEndsAt(sr.getEndsAt());
                activated.setIsSubscribed(true);
                activated.setStatus("ACTIVATE");

                processor.output().send(activated.toMessage());
                log.info("📩 SubscriptionActivated 발행 (userId={})", uid);
            }

            /* ▼ 베스트셀러 집계 (도서-차감) */
            if (event instanceof SubscriptionStatusChecked) {
                SubscriptionStatusChecked s = (SubscriptionStatusChecked) event;
                if (!s.getIsSubscribed() && amt.signum() < 0) {
                    handleBestsellerLogic(s.getBookId(), Math.abs(amt.longValue()));
                }
            }

            log.info("▶ 잔액 변동 {}P (userId={}, 잔액={})",
                     amt, uid, point.getCurrentAmount());
            return;
        }

        /* ─── PointUseFailed 직접 수신 ─── */
        if ("PointUseFailed".equals(type)) {
            PointUseFailed e = mapper.readValue(payload, PointUseFailed.class);
            log.info("✖ 수신된 PointUseFailed userId={}, shortage={}",
                     e.getUserId(), e.getShortage());
        }
    }

    /* ───────── userId 추출 ───────── */
    private String extractUserId(AbstractEvent ev) {
        switch (ev.getEventType()) {
            case "BasicPointGranted":          return ((BasicPointGranted) ev).getUserId();
            case "BonusPointGranted":          return ((BonusPointGranted) ev).getUserId();
            case "PointDeducted":              return ((PointDeducted) ev).getUserId();
            case "PointUseFailed":             return ((PointUseFailed) ev).getUserId();
            case "MemberJoined":               return ((MemberJoined) ev).getUserId();
            case "PointPurchaseRequested":     return ((PointPurchaseRequested) ev).getUserId();
            case "SubscriptionRequested":      return ((SubscriptionRequested) ev).getUserId();
            case "SubscriptionStatusChecked":  return ((SubscriptionStatusChecked) ev).getUserId();
            case "BookPurchaseRequested":      return ((BookPurchaseRequested) ev).getUserId();
            default:
                throw new IllegalArgumentException("userId 추출 불가: " + ev.getEventType());
        }
    }

    /* ───────── 베스트셀러 로직 ───────── */
    private void handleBestsellerLogic(String bookId, Long price) {
        if (bookId == null || price <= 0) return;

        BestsellerTracker tr = bestsellerRepo.findById(bookId)
                                             .orElse(new BestsellerTracker());

        tr.setBookId(bookId);
        tr.increment();
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
