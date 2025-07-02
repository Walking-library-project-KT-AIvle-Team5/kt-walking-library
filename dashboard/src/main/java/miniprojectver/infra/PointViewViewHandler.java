package miniprojectver.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import miniprojectver.config.kafka.KafkaProcessor;
import miniprojectver.domain.PointDeducted;
import miniprojectver.domain.PointView;

@Service
public class PointViewViewHandler {

    @Autowired
    private PointViewRepository pointViewRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointDeducted_then_UPDATE_1(
        @Payload PointDeducted pointDeducted
    ) {
        try {
            if (!pointDeducted.validate()) return;

            System.out.println("=== PointDeducted event consumed ===");
            System.out.println(pointDeducted.toJson());

            // userId로 기존 포인트 조회
            PointView pointView = pointViewRepository.findByUserId(pointDeducted.getUserId())
                .orElse(new PointView());

            pointView.setUserId(pointDeducted.getUserId());

            Integer oldBalance = pointView.getPointBalance() != null
                ? pointView.getPointBalance()
                : 0;

            pointView.setPointBalance(oldBalance - pointDeducted.getAmountPoint());

            pointViewRepository.save(pointView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
