package miniprojectver.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class FetchSubscriberListViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private FetchSubscriberListRepository fetchSubscriberListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionRequested_then_CREATE_1(
        @Payload SubscriptionRequested subscriptionRequested
    ) {
        try {
            if (!subscriptionRequested.validate()) return;

            // view 객체 생성
            FetchSubscriberList fetchSubscriberList = new FetchSubscriberList();
            // view 객체에 이벤트의 Value 를 set 함
            fetchSubscriberList.setSubscriptionId(
                subscriptionRequested.getSubscriptionId()
            );
            fetchSubscriberList.setUserId(subscriptionRequested.getUserId());
            fetchSubscriberList.setStartedAt(
                subscriptionRequested.getStartedAt()
            );
            fetchSubscriberList.setEndAt(subscriptionRequested.getEndsAt());
            // view 레파지 토리에 save
            fetchSubscriberListRepository.save(fetchSubscriberList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionCancelled_then_UPDATE_1(
        @Payload SubscriptionCancelled subscriptionCancelled
    ) {
        try {
            if (!subscriptionCancelled.validate()) return;
            // view 객체 조회

            List<FetchSubscriberList> fetchSubscriberListList = fetchSubscriberListRepository.findBySubscriptionId(
                subscriptionCancelled.getSubscriptionId()
            );
            for (FetchSubscriberList fetchSubscriberList : fetchSubscriberListList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                fetchSubscriberList.setEndAt(
                    subscriptionCancelled.getCancelledAt()
                );
                fetchSubscriberList.setStatus(
                    subscriptionCancelled.getStatus()
                );
                // view 레파지 토리에 save
                fetchSubscriberListRepository.save(fetchSubscriberList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionCancelled_then_UPDATE_2(
        @Payload SubscriptionCancelled subscriptionCancelled
    ) {
        try {
            if (!subscriptionCancelled.validate()) return;
            // view 객체 조회

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
