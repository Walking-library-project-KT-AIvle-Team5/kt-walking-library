// src/main/java/miniprojectver/infra/PointRequestManagementController.java (예시)
package miniprojectver.infra;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import miniprojectver.domain.*; // PointRequestManagement, PointRequestManagementRepository 임포트
import miniprojectver.dto.*;
import javax.validation.Valid; // @Valid 어노테이션을 위한 임포트

@RestController
@RequestMapping("/pointRequestManagements") // 명확한 경로 설정 (plural)
@Transactional // 트랜잭션 처리
public class PointRequestManagementController {

    @Autowired
    PointRequestManagementRepository pointRequestManagementRepository;

    @PostMapping // POST /pointRequestManagements 요청 처리
    public ResponseEntity<PointRequestManagement> requestPointPurchase(
        @Valid @RequestBody RequestPointPurchaseCommand requestPointPurchaseCommand // <-- DTO 사용 및 유효성 검사 활성화
    ) {
        try {
            // DTO의 데이터를 사용하여 도메인 팩토리 메서드 호출
            PointRequestManagement newRequest = PointRequestManagement.requestPointPurchase(
                requestPointPurchaseCommand.getUserId(),
                // requestPointPurchaseCommand.getRequestedPointAmount(),
                requestPointPurchaseCommand.getPaymentMethodId(),
                requestPointPurchaseCommand.getActualPaymentAmount()
            );

            // 애그리게이트를 저장하여 영속화
            pointRequestManagementRepository.save(newRequest);

            // 성공 응답 반환
            return new ResponseEntity<>(newRequest, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            // 도메인 내부 유효성 검사 실패 시 (예: 값이 0 이하)
            System.err.println("Validation Error: " + e.getMessage()); // 오류 로그
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            // 그 외 예상치 못한 오류 발생 시
            e.printStackTrace(); // 상세 오류 로그
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}