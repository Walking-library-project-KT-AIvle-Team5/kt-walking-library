package miniprojectver.infra;

import lombok.RequiredArgsConstructor;
import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.Point;
import miniprojectver.message.*;     // ↙︎ 수신 이벤트 DTO 모음
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PolicyHandler {

    /* 1. 회원 가입됨  */
    @StreamListener(value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MemberJoined'")
    public void onMemberJoined(@Payload MemberJoined evt) {
        Point.grantPointOnMemberJoined(evt);
    }

    /* 2. 포인트 구매(충전) 요청 */
    @StreamListener(value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointPurchaseRequested'")
    public void onPointPurchase(@Payload PointPurchaseRequested evt) {
        Point.chargePoint(evt);
    }

    /* 3-A. 구독 신청 */
    @StreamListener(value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionRequested'")
    public void onSubscription(@Payload SubscriptionRequested evt) {
        Point.deductForSubscription(evt);
    }

    /* 3-B. 도서 구매 요청 */
    @StreamListener(value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookPurchaseRequested'")
    public void onBookPurchase(@Payload BookPurchaseRequested evt) {
        Point.deductForBook(evt);
    }
}
