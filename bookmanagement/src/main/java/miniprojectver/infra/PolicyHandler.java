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
    BookRepository bookRepository;

    @Autowired
    ManuscriptRepository manuscriptRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookPublicationChecked'"
    )
    public void wheneverBookPublicationChecked_RegisterBook(
        @Payload BookPublicationChecked bookPublicationChecked
    ) {
        BookPublicationChecked event = bookPublicationChecked;
        System.out.println(
            "\n\n##### listener RegisterBook : " +
            bookPublicationChecked +
            "\n\n"
        );

        // Sample Logic //
        Book.registerBook(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointDeducted'"
    )
    public void wheneverPointDeducted_MarkAsBestseller(
        @Payload PointDeducted pointDeducted
    ) {
        PointDeducted event = pointDeducted;
        System.out.println(
            "\n\n##### listener MarkAsBestseller : " + pointDeducted + "\n\n"
        );

        // Sample Logic //
        Book.markAsBestseller(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
