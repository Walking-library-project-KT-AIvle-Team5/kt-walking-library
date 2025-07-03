package miniprojectver.infra;

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
        System.out.println(
            "\n\n##### listener RegisterBook : " + bookPublicationChecked + "\n\n"
        );

        if (BookStatus.PUBLISHED.equals(bookPublicationChecked.getStatus())) {
            if (!bookRepository.existsById(bookPublicationChecked.getId())) {
                Book.registerBook(bookPublicationChecked);
            } else {
                System.out.println("Skipped: Book already registered");
            }
        } else {
            System.out.println("Skipped: BookPublicationChecked status is not PUBLISHED");
        }
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointDeducted'"
    )
    public void wheneverPointDeducted_MarkAsBestseller(
        @Payload PointDeducted pointDeducted
    ) {
        System.out.println(
            "\n\n##### listener MarkAsBestseller : " + pointDeducted + "\n\n"
        );

        Book.markAsBestseller(pointDeducted);
    }
}
//>>> Clean Arch / Inbound Adaptor
