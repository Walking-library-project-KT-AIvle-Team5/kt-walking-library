package miniprojectver.infra;

import java.io.IOException;
import java.util.Date;
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

    @Autowired
    private FetchSubscriberListRepository fetchSubscriberListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionRequested_then_CREATE_1(
        @Payload SubscriptionRequested subscriptionRequested
    ) {
        try {
            if (!subscriptionRequested.validate()) return;

            // ✅ consume 로그 추가
            System.out.println("=== SubscriptionRequested event consumed ===");
            System.out.println(subscriptionRequested.toJson());

            FetchSubscriberList fetchSubscriberList = new FetchSubscriberList();

            fetchSubscriberList.setSubscriptionId(
                subscriptionRequested.getSubscriptionId()
            );
            fetchSubscriberList.setUserId(subscriptionRequested.getUserId());

            // ✅ Long → Date 변환
            fetchSubscriberList.setStartedAt(
                subscriptionRequested.getStartedAt() != null
                    ? new Date(subscriptionRequested.getStartedAt())
                    : null
            );
            fetchSubscriberList.setEndAt(
                subscriptionRequested.getEndsAt() != null
                    ? new Date(subscriptionRequested.getEndsAt())
                    : null
            );

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

            List<FetchSubscriberList> fetchSubscriberListList = fetchSubscriberListRepository.findBySubscriptionId(
                subscriptionCancelled.getSubscriptionId()
            );
            for (FetchSubscriberList fetchSubscriberList : fetchSubscriberListList) {
                // ✅ Long → Date 변환
                fetchSubscriberList.setEndAt(
                    subscriptionCancelled.getCancelledAt() != null
                        ? new Date(subscriptionCancelled.getCancelledAt())
                        : null
                );
                fetchSubscriberList.setStatus(subscriptionCancelled.getStatus());

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
            // (현재 내용 없음)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
