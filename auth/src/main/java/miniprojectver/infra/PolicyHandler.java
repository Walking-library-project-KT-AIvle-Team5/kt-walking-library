package miniprojectver.infra;

import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    MemberRepository memberRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // MemberSignedUp 이벤트가 발생했을 때 이 메서드가 자동으로 실행됩니다.
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MemberSignedUp'"
    )
    public void wheneverMemberSignedUp_RequestKtAuthentication(
        @Payload MemberSignedUp memberSignedUp
    ) {
        System.out.println(
            "\n\n##### listener RequestKtAuthentication : " + memberSignedUp + "\n\n"
        );

        // 받은 이벤트에서 isKtCustomer가 YES인지 확인합니다.
        if (memberSignedUp.getIsKtCustomer() == YesNo.YES) {
            
            System.out.println("KT 고객으로 가입되었습니다. 인증 절차를 시작합니다.");

            // 1. 새로운 KT 고객 인증 요청 이벤트를 생성합니다.
            KtAuthenticationRequested event = new KtAuthenticationRequested();
            
            // 2. 어떤 회원에 대한 인증 요청인지 ID를 설정합니다.
            event.setMemberId(memberSignedUp.getId());
            
            // 3. 이벤트를 발행합니다.
            event.publish();
        }
    }
}