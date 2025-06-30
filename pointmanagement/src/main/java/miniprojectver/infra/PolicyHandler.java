package miniprojectver.infra;

import javax.transaction.Transactional;
import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.*;
import miniprojectver.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 1. 회원 가입 시 기본/보너스 포인트 지급
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='MemberJoined'")
    public void wheneverMemberJoined_GrantBasicPoint(@Payload MemberJoined memberJoined) {
        if (memberJoined == null || memberJoined.getUserId() == null) return;
        System.out.println("✅ listener GrantBasicPoint : " + memberJoined);
        Point.grantBasicPoint(memberJoined);
    }

    // 2. 구독 상태 확인 → 포인트 차감 시도
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='SubscriptionStatusChecked'")
    public void wheneverSubscriptionStatusChecked_TryPointDeduction(@Payload SubscriptionStatusChecked event) {
        if (event == null || event.getUserId() == null) return;
        System.out.println("✅ listener TryPointDeduction (StatusChecked) : " + event);
        Point.tryPointDeduction(event);
    }

    // 3. 구독 요청 → 포인트 차감 시도
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='SubscriptionRequested'")
    public void wheneverSubscriptionRequested_TryPointDeduction(@Payload SubscriptionRequested event) {
        if (event == null || event.getUserId() == null) return;
        System.out.println("✅ listener TryPointDeduction (Requested) : " + event);
        Point.tryPointDeduction(event);
    }

    // 4. 포인트 충전 커맨드 수신
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='ChargePointCommand'")
    public void wheneverChargePointCommand_ChargePoint(@Payload ChargePointCommand command) {
        if (command == null || command.getUserId() == null) return;
        System.out.println("✅ listener ChargePoint : " + command);
        Point.chargePoint(command);
    }

    // 5. 포인트 사용 커맨드 수신
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='UsePointCommand'")
    public void wheneverUsePointCommand_UsePoint(@Payload UsePointCommand command) {
        if (command == null || command.getUserId() == null) return;
        System.out.println("✅ listener UsePoint : " + command);
        Point.usePoint(command);
    }
}
