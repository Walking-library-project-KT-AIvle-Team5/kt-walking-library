package miniprojectver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class SubscriptionCheckService {

    @Value("${subscription.api.url}")
    private String subscriptionApiUrl; // ex) http://subscribermanagement:8082/api/subscribers/

    @Autowired
    private RestTemplate restTemplate;

    public boolean isUserSubscribed(String userId) {
        try {
            // ex: http://subscribermanagement:8082/api/subscribers/abc123
            String url = subscriptionApiUrl + userId;

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            // 응답 객체에 status가 ACTIVE면 true 반환
            return response != null && "ACTIVE".equals(response.get("status"));

        } catch (Exception e) {
            System.out.println("구독 정보 조회 실패: " + e.getMessage());
            return false;
        }
    }
}
