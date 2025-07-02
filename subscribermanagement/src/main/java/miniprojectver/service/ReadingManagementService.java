package miniprojectver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 이 임포트가 있는지 확인

import miniprojectver.domain.ReadingManagement; // ReadingManagement 임포트
import miniprojectver.domain.ReadingManagementRepository; // ReadingManagementRepository 임포트

@Service
@Transactional // <-- 이 어노테이션이 반드시 있어야 합니다!
public class ReadingManagementService {

    @Autowired
    private ReadingManagementRepository readingManagementRepository; // 구독 관리 리포지토리가 아닌 독서 관리 리포지토리

    // 독서 시작
    public ReadingManagement startReading(String userId, String bookId, Integer initialPage) {
        ReadingManagement newReading = ReadingManagement.startReading(
            userId,
            bookId,
            initialPage
        );
        // save 호출 후 트랜잭션이 커밋될 때 데이터가 DB에 반영됩니다.
        readingManagementRepository.save(newReading); 
        return newReading;
    }

    // 독서 진행률 업데이트
    public ReadingManagement updateProgress(Long readingActivityId, Integer newPage) {
        ReadingManagement existingReading = readingManagementRepository.findById(readingActivityId)
            .orElseThrow(() -> new IllegalArgumentException("Reading activity not found: " + readingActivityId));
        
        existingReading.updateProgress(newPage); // 애그리게이트 메서드 호출
        // save를 명시적으로 호출하지 않아도 @Transactional 내에서는 변경 감지(Dirty Checking)로 자동 반영될 수 있음
        // 명시적 save는 가독성이나 즉시 반영(플러시)이 필요할 때 사용
        readingManagementRepository.save(existingReading); 
        return existingReading;
    }

    // 독서 완료
    public ReadingManagement completeReading(Long readingActivityId, Integer finalPage) {
        ReadingManagement existingReading = readingManagementRepository.findById(readingActivityId)
            .orElseThrow(() -> new IllegalArgumentException("Reading activity not found: " + readingActivityId));
        
        existingReading.completeReading(finalPage); // 애그리게이트 메서드 호출
        readingManagementRepository.save(existingReading);
        return existingReading;
    }
}