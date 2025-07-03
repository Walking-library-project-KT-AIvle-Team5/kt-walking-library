package miniprojectver.aiservice.service;

import org.springframework.stereotype.Service;

@Service
public class PriceCalculationService {

    /**
     * 현재는 테스트를 위해 가격을 무조건 11,000원으로 반환합니다.
     *
     * @return 계산된 구독 가격 (원)
     */
    public String calculatePrice() {
        int fixedPrice = 11000; // 무조건 11,000원으로 고정
        return String.format("구독 가격은 %d원 입니다.", fixedPrice);
    }
}