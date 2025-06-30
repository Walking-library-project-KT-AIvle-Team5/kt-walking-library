package miniprojectver;

import miniprojectver.config.kafka.KafkaProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(KafkaProcessor.class) // Kafka 메시지 채널을 사용하도록 활성화합니다.
public class AuthApplication {

    // 다른 클래스(AbstractEvent)에서 Spring의 Bean을 가져다 쓸 수 있도록 context를 저장하는 static 변수입니다.
    public static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(AuthApplication.class, args);
    }
}