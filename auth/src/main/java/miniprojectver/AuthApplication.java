package miniprojectver;

import miniprojectver.config.kafka.KafkaProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(KafkaProcessor.class) // 우리가 만든 KafkaProcessor를 사용하도록 설정
public class AuthApplication {
    public static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(AuthApplication.class, args);
    }
}
