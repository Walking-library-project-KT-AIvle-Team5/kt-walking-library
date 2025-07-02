package miniprojectver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding; // ✅ 이거 추가!
import miniprojectver.config.kafka.KafkaProcessor;           // ✅ KafkaProcessor import도 필요

@SpringBootApplication
@EnableBinding(KafkaProcessor.class)
public class PointmanagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(PointmanagementApplication.class, args);
    }
}
