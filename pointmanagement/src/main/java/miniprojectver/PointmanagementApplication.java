package miniprojectver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;   // ✅
import miniprojectver.config.kafka.KafkaProcessor;             // ✅

@SpringBootApplication
@EnableBinding(KafkaProcessor.class)   // ✅ Kafka 바인딩 등록
public class PointmanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PointmanagementApplication.class, args);
    }
}
