package miniprojectver.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional; // Spring의 @Transactional이 더 일반적으로 사용됩니다.
import miniprojectver.domain.*;
import miniprojectver.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // HttpStatus import
import org.springframework.http.ResponseEntity; // ResponseEntity import
import org.springframework.web.bind.annotation.*; // @PostMapping, @RequestBody 등 사용을 위해 와일드카드 import
// import org.springframework.web.bind.annotation.RequestMapping; // 불필요시 제거
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookPurchaseManagements") // <-- 이 주석을 풀고 RequestMapping을 다시 활성화!
@Transactional // 컨트롤러 전체에 트랜잭션 적용
public class BookPurchaseManagementController {

    @Autowired
    BookPurchaseManagementRepository bookPurchaseManagementRepository;

    // --- [1] 도서 구매 요청 생성 API ---
    @PostMapping // POST /bookPurchaseManagements 요청 처리
    public ResponseEntity<BookPurchaseManagement> requestBookPurchase(
        @RequestBody RequestBookPurchaseCommand requestBookPurchaseCommand // 요청 바디를 받을 DTO
    ) {
        try {
            // 도메인 애그리게이트 루트의 팩토리 메서드를 통해 새 객체 생성
            BookPurchaseManagement newPurchase = BookPurchaseManagement.requestBookPurchase(
                requestBookPurchaseCommand.getUserId(),
                requestBookPurchaseCommand.getBookId(),
                requestBookPurchaseCommand.getPrice(),
                requestBookPurchaseCommand.getPoint()
            );

            // 생성된 객체를 Repository를 통해 저장
            bookPurchaseManagementRepository.save(newPurchase);

            // 성공 응답 반환
            return new ResponseEntity<>(newPurchase, HttpStatus.CREATED); // 201 Created 응답
        } catch (IllegalArgumentException e) {
            // 유효성 검사 실패 시 400 Bad Request 반환
            e.printStackTrace(); // 로그에 오류 출력
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 그 외 예외 발생 시 500 Internal Server Error 반환
            e.printStackTrace(); // 로그에 오류 출력
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- (이전에 독서관리에서 사용했던 PATCH 메서드 예시도 여기에 추가될 수 있음) ---
    // @PatchMapping("/{id}")
    // public ResponseEntity<ReadingManagement> updateReadingProgress(
    //     @PathVariable Long id,
    //     @RequestBody UpdateProgressCommand updateProgressCommand
    // ) {
    //     // ... 기존 로직 ...
    // }
}
