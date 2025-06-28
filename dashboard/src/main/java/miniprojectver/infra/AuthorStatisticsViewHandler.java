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
public class AuthorStatisticsViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private AuthorStatisticsRepository authorStatisticsRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointDeducted_then_CREATE_1(
        @Payload PointDeducted pointDeducted
    ) {
        try {
            if (!pointDeducted.validate()) return;

            // view 객체 생성
            AuthorStatistics authorStatistics = new AuthorStatistics();
            // view 객체에 이벤트의 Value 를 set 함
            authorStatistics.setId(Long.valueOf(pointDeducted.getUserId()));
            // view 레파지 토리에 save
            authorStatisticsRepository.save(authorStatistics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
