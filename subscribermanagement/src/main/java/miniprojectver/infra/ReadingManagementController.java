package miniprojectver.infra;

import miniprojectver.domain.ReadingManagement; // ReadingManagement 엔티티 import
import miniprojectver.domain.ReadingManagementRepository; // ReadingManagementRepository import

// StartReadingCommand DTO가 위치한 패키지에 따라 달라질 수 있습니다..
import miniprojectver.dto.StartReadingCommand;
import miniprojectver.dto.UpdateProgressCommand;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // HttpStatus import
import org.springframework.http.ResponseEntity; // ResponseEntity import
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // @PostMapping import
import org.springframework.web.bind.annotation.RequestBody; // @RequestBody import
import org.springframework.web.bind.annotation.RequestMapping; // @RequestMapping import
import org.springframework.web.bind.annotation.RestController; // @RestController import
import javax.transaction.Transactional; // @Transactional import (javax 패키지 확인)

@RestController
@RequestMapping(value = "/readingManagements")
@Transactional
public class ReadingManagementController {

    @Autowired
    ReadingManagementRepository readingManagementRepository;

    @PostMapping
    public ResponseEntity<ReadingManagement> startReading(
        @RequestBody StartReadingCommand startReadingCommand // <-- 여기가 중요!
    ) {
        try {
            // StartReadingCommand에서 받은 데이터를 사용하여 도메인 메서드 호출
            ReadingManagement newReading = ReadingManagement.startReading(
                startReadingCommand.getUserId(),
                startReadingCommand.getBookId(),
                startReadingCommand.getInitialPage()
            );
            readingManagementRepository.save(newReading);
            return new ResponseEntity<>(newReading, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{readingActivityId}") // <-- @GetMapping 어노테이션 사용
    public ResponseEntity<ReadingManagement> getReadingManagement(@PathVariable Long readingActivityId) {
        Optional<ReadingManagement> readingManagementOptional = readingManagementRepository.findById(readingActivityId); // <-- Repository의 findById 메서드 사용

        if (readingManagementOptional.isPresent()) {
            return new ResponseEntity<>(readingManagementOptional.get(), HttpStatus.OK); // 찾으면 200 OK와 객체 반환
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없으면 404 반환
        }
    }

    @PatchMapping("/{readingActivityId}") // URL 경로에 readingActivityId 포함
    public ResponseEntity<ReadingManagement> updateReadingProgress(
        @PathVariable Long readingActivityId, // URL 경로의 ID를 받아옴
        @RequestBody UpdateProgressCommand updateProgressCommand // 요청 본문의 DTO를 받아옴
    ) { // <-- 여기에 여는 중괄호가 있어야 해요
        try {
            // 1. 주어진 ID로 독서 활동을 데이터베이스에서 찾아요.
            Optional<ReadingManagement> optionalReading = readingManagementRepository.findById(readingActivityId);

            // 2. 만약 해당 ID의 독서 활동을 찾을 수 없다면 404 Not Found 응답을 보냅니다.
            if (!optionalReading.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // 3. 독서 활동을 찾았다면, Optional에서 실제 객체를 꺼냅니다.
            ReadingManagement readingManagement = optionalReading.get();

            // 4. 도메인 모델의 'updateProgress' 비즈니스 행위를 호출하여 상태를 변경합니다.
            //    UpdateProgressCommand DTO에서 새로운 페이지 정보를 가져와 전달합니다.
            readingManagement.updateProgress(updateProgressCommand.getCurrentPage());

            // 5. 변경된 엔티티를 데이터베이스에 저장합니다.
            //    @Transactional 어노테이션이 있다면 변경사항이 자동으로 반영되지만,
            //    명시적으로 save를 호출하는 것이 더 명확하고 안전합니다.
            readingManagementRepository.save(readingManagement);

            // 6. 성공적으로 업데이트되었음을 200 OK 상태 코드와 함께 업데이트된 객체를 반환합니다.
            return new ResponseEntity<>(readingManagement, HttpStatus.OK);

        } catch (Exception e) {
            // 7. 처리 중 예외가 발생하면, 에러를 콘솔에 출력하고 500 Internal Server Error를 반환합니다.
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } // <-- 여기에 닫는 중괄호가 있어야 해요

    @DeleteMapping("/{readingActivityId}") // <-- @DeleteMapping 어노테이션 사용
    public ResponseEntity<Void> deleteReadingManagement(@PathVariable Long readingActivityId) {
        try {
            // 해당 ID의 독서 활동이 존재하는지 확인
            if (!readingManagementRepository.existsById(readingActivityId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없으면 404 반환
            }

            readingManagementRepository.deleteById(readingActivityId); // <-- Repository의 deleteById 메서드 사용

            // 성공적으로 삭제되었지만, 반환할 본문 내용이 없을 때 204 No Content 반환
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}