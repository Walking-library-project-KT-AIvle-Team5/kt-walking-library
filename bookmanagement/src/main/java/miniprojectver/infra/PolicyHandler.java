package miniprojectver.infra;

import javax.transaction.Transactional;
import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    BookRepository bookRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='BookPublicationChecked'")
    public void wheneverBookPublicationChecked_RegisterBook(@Payload BookPublicationChecked event) {
        Book.registerBook(event);
    }

    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='PointDeducted'")
    public void wheneverPointDeducted_MarkAsBestseller(@Payload PointDeducted event) {
        Book.markAsBestseller(event);
    }

    // Manuscript 출간 요청 수신 → Book 출간 심사 및 Book 생성
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='ManuscriptPublicationRequested'")
    public void wheneverManuscriptPublicationRequested_Handle(@Payload ManuscriptPublicationRequested event) {
        System.out.println("Received ManuscriptPublicationRequested: " + event);

        Book newBook = new Book();
        newBook.setBookName(event.getTitle());
        newBook.setAuthorId(event.getAuthorId());
        newBook.setCategory(event.getCategory());
        newBook.setSummary(event.getSummary());
        newBook.setImagePath(event.getImagePath());
        newBook.setContents(event.getContent());
        newBook.setPrice(event.getPrice());
        newBook.setStatus(Status.PUBLISHED);

        bookRepository.save(newBook);

        BookPublicationChecked checkedEvent = new BookPublicationChecked(newBook);
        checkedEvent.publishAfterCommit();
    }

    // AI 표지 생성 요청 수신 → AI 시스템 연계 (예: 외부 API, Kafka pub)
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='AiCoverImageRequested'")
    public void wheneverAiCoverImageRequested_Handle(@Payload AiCoverImageRequested event) {
        System.out.println("Received AiCoverImageRequested: " + event);
        // TODO: AI 표지 생성 시스템 연계 로직 작성
    }

    // AI 전자책 분석 요청 수신 → AI 시스템 연계
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='EbookAnalysisRequested'")
    public void wheneverEbookAnalysisRequested_Handle(@Payload EbookAnalysisRequested event) {
        System.out.println("Received EbookAnalysisRequested: " + event);
        // TODO: AI 분석 시스템 연계 로직 작성
    }
}

//>>> Clean Arch / Inbound Adaptor
