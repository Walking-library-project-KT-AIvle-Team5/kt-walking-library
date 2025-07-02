package miniprojectver.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.BestsellerRegistered;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import miniprojectver.domain.BestsellerReadModelRepository;
import miniprojectver.domain.BestsellerReadModel;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BestsellerPolicyHandler {

    private final BestsellerReadModelRepository repository;

    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='BestsellerRegistered'")
    public void onBestsellerRegistered(@Payload String message) throws Exception {
        BestsellerRegistered evt = new ObjectMapper().readValue(message, BestsellerRegistered.class);
        log.info("📘 BestsellerRegistered 이벤트 수신: {}", evt);

        if (!repository.existsById(evt.getBookId())) {
            repository.save(BestsellerReadModel.builder()
                .bookId(evt.getBookId())
                .totalPurchases(5L)  // 첫 등록 기준으로 5회 구매
                .build());
        }
    }
}
