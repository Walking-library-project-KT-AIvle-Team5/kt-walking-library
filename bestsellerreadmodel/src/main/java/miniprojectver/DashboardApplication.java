package miniprojectver;

import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.infra.AbstractEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableBinding(KafkaProcessor.class)
@EnableFeignClients
public class DashboardApplication {

    public static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(DashboardApplication.class, args);

        // ✅ AbstractEvent에 applicationContext를 주입하여 publish()에서 사용 가능하게 함
        AbstractEvent.setApplicationContext(applicationContext);
    }
}
