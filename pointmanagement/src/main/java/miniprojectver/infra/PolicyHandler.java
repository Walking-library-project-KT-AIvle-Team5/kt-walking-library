package miniprojectver.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import miniprojectver.domain.*;
import miniprojectver.config.kafka.KafkaProcessor;

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

    /* ---------------------------
     * 1. 회원가입 시 포인트 지급
     * --------------------------- */
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='MemberJoined'")
    public void onMemberJoined(@Payload String message) throws Exception {
        MemberJoined evt = new ObjectMapper().readValue(message, MemberJoined.class);
        log.info("▶ MemberJoined received: {}", evt);

        log.info("▶ userId type = {}", evt.getUserId().getClass().getName());

        Point point = Point.create(String.valueOf(evt.getUserId()), evt.getIsktCustomer());
        pointRepo.save(point);

        BasicPointGranted basicEvt = new BasicPointGranted(evt.getUserId(), 1000L);
        processor.output().send(basicEvt.toMessage());

        if (Boolean.TRUE.equals(evt.getIsktCustomer())) {
            BonusPointGranted bonusEvt = new BonusPointGranted(evt.getUserId(), 5000L);
            processor.output().send(bonusEvt.toMessage());
        }
    }

    /* ---------------------------
     * 2. 포인트 충전
     * --------------------------- */
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='PointPurchaseRequested'")
    public void onPointPurchase(@Payload String message) throws Exception {
        PointPurchaseRequested evt = new ObjectMapper().readValue(message, PointPurchaseRequested.class);
        log.info("▶ PointPurchaseRequested received: {}", evt);

        Point point = pointRepo.findByUserId(evt.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        point.charge(evt.getAmount());
        pointRepo.save(point);

        processor.output().send(evt.toMessage());
    }

    /* ---------------------------
     * 3. 구독여부 확인 결과 처리
     * --------------------------- */
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='SubscriptionStatusChecked'")
    public void onSubscriptionStatusChecked(@Payload String message) throws Exception {
        SubscriptionStatusChecked evt = new ObjectMapper().readValue(message, SubscriptionStatusChecked.class);
        log.info("▶ SubscriptionStatusChecked received: {}", evt);

        String userId = String.valueOf(evt.getUserId());
        Long bookId = evt.getBookId();
        Boolean isSubscribed = evt.getIsSubscribed();
        Long price = evt.getPrice();

        long usedAmount = Boolean.TRUE.equals(isSubscribed) ? 0L : price;

        Point point = pointRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보 없음: userId=" + userId));

        boolean success = point.use(usedAmount);
        pointRepo.save(point);

        if (success) {
            PointDeducted deductedEvt = new PointDeducted(userId, usedAmount, bookId);
            processor.output().send(deductedEvt.toMessage());

            if (bookId != null && usedAmount > 0) {
                BestsellerTracker tracker = bestsellerRepo.findById(bookId)
                        .orElse(BestsellerTracker.builder()
                                .bookId(bookId)
                                .purchaseCount(0L)
                                .bestseller(false)
                                .build());

                tracker.increment();
                bestsellerRepo.save(tracker);

                // ✅ 베스트셀러 등록 조건 확인 및 처리
                if (!tracker.getBestseller() && tracker.getPurchaseCount() >= 5) {
                    tracker.setBestseller(true);
                    bestsellerRepo.save(tracker);

                    BestsellerRegistered bsEvt = new BestsellerRegistered(bookId);
                    processor.output().send(bsEvt.toMessage());
                }
            }
        } else {
            PointUseFailed failedEvt = new PointUseFailed(userId, usedAmount, bookId);
            processor.output().send(failedEvt.toMessage());
        }
    }
}
