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
    BookPublicationRepository bookPublicationRepository;

    @Autowired
    PlatformManagementRepository platformManagementRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookPurchaseRequested'"
    )
    public void wheneverBookPurchaseRequested_CheckSubscription(
        @Payload BookPurchaseRequested bookPurchaseRequested
    ) {
        BookPurchaseRequested event = bookPurchaseRequested;
        System.out.println(
            "\n\n##### listener CheckSubscription : " +
            bookPurchaseRequested +
            "\n\n"
        );

        // Sample Logic //
        PlatformManagement.checkSubscription(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ManuscriptPublicationRequested'"
    )
    public void wheneverManuscriptPublicationRequested_RequestBookPublication(
        @Payload ManuscriptPublicationRequested manuscriptPublicationRequested
    ) {
        ManuscriptPublicationRequested event = manuscriptPublicationRequested;
        System.out.println(
            "\n\n##### listener RequestBookPublication : " +
            manuscriptPublicationRequested +
            "\n\n"
        );

        // Sample Logic //
        BookPublication.requestBookPublication(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
