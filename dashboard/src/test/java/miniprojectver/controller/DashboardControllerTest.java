package miniprojectver.controller;

import miniprojectver.domain.*;
import miniprojectver.infra.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// ✅ 통합 테스트임을 선언
@SpringBootTest
@AutoConfigureMockMvc
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FetchSubscriberListRepository subscriberRepo;

    @Test
    public void testGetSubscribers() throws Exception {
        // 🔥 given: 테스트 데이터 준비
        FetchSubscriberList subscriber = new FetchSubscriberList();
        subscriber.setUserId("testuser");
        subscriberRepo.save(subscriber);

        // 🔥 when & then: API 호출 후 결과 검증
        mockMvc.perform(get("/dashboard/subscribers"))
                .andExpect(status().isOk());
                // .andExpect(jsonPath("$[0].userId").value("testuser"));
                // 위 라인 추가 시 응답 body 값까지 검증 가능
    }
}
