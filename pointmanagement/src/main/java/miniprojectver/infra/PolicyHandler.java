package miniprojectver.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
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
    PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionStatusChecked'"
    )
    public void wheneverSubscriptionStatusChecked_TryPointDeduction(
        @Payload SubscriptionStatusChecked subscriptionStatusChecked
    ) {
        SubscriptionStatusChecked event = subscriptionStatusChecked;
        System.out.println(
            "\n\n##### listener TryPointDeduction : " +
            subscriptionStatusChecked +
            "\n\n"
        );

        // Sample Logic //
        Point.tryPointDeduction(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionRequested'"
    )
    public void wheneverSubscriptionRequested_TryPointDeduction(
        @Payload SubscriptionRequested subscriptionRequested
    ) {
        SubscriptionRequested event = subscriptionRequested;
        System.out.println(
            "\n\n##### listener TryPointDeduction : " +
            subscriptionRequested +
            "\n\n"
        );

        // Sample Logic //
        Point.tryPointDeduction(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MemberJoined'"
    )
    public void wheneverMemberJoined_GrantBasicPoint(
        @Payload MemberJoined memberJoined
    ) {
        MemberJoined event = memberJoined;
        System.out.println(
            "\n\n##### listener GrantBasicPoint : " + memberJoined + "\n\n"
        );

        // Sample Logic //
        Point.grantBasicPoint(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
