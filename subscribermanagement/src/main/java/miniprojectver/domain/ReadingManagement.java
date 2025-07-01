package miniprojectver.domain;

// import java.util.Date; // <-- 제거
import java.time.LocalDateTime; // <-- 추가

import javax.persistence.*;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;

import miniprojectver.domain.ProgressUpdated;
import miniprojectver.domain.ReadingCompleted;
import miniprojectver.domain.ReadingStarted;
import miniprojectver.domain.ReadingStatus;

@Entity
@Table(name = "reading_management")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "readingActivityId", callSuper = false)
@Access(AccessType.FIELD)
public class ReadingManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long readingActivityId;

    private String userId;
    private String bookId;

    // LocalDateTime으로 타입 변경
    @Column(name = "started_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime startedAt;

    @Column(name = "completed_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime completedAt;

    @Column(name = "last_updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdatedAt;

    @Column(name = "current_page") // INTEGER에 해당
    private Integer currentPage;

    @Column(name = "reading_status") // VARCHAR에 해당 (Enum이므로)
    @Enumerated(EnumType.STRING)
    private ReadingStatus readingStatus;

    // --- [1] 비즈니스 행위: 독서 시작 (Command) - 팩토리 메서드 ---
    public static ReadingManagement startReading(String userId, String bookId, Integer initialPage) {
        // ... (유효성 검증 동일)

        ReadingManagement newReading = new ReadingManagement();
    newReading.userId = userId;
    newReading.bookId = bookId;
    newReading.currentPage = initialPage;
    newReading.readingStatus = ReadingStatus.STARTED;

    // LocalDateTime.now()로 현재 시간 할당
    newReading.startedAt = LocalDateTime.now();
    newReading.lastUpdatedAt = newReading.startedAt; // 시작 시점이 마지막 업데이트 시점

    // ReadingStarted 이벤트 발행
    new ReadingStarted(newReading).publishAfterCommit(); // <-- 이 줄을 추가해야 합니다!

    return newReading;
    }

    // --- [2] 비즈니스 행위: 독서 진행률 업데이트 (Command) ---
    public void updateProgress(Integer currentPage) {
        // ... (불변 조건 검사 동일)

        this.currentPage = currentPage;
        this.readingStatus = ReadingStatus.IN_PROGRESS;
        this.lastUpdatedAt = LocalDateTime.now(); // 현재 시간으로 업데이트

        new ProgressUpdated(this).publishAfterCommit();
    }

    // --- [3] 비즈니스 행위: 독서 완료 (Command) ---
    public void completeReading(Integer finalPage) {
        // ... (불변 조건 검사 동일)

        this.currentPage = finalPage;
        this.readingStatus = ReadingStatus.COMPLETED;
        this.completedAt = LocalDateTime.now(); // 현재 시간으로 완료 시점 기록
        this.lastUpdatedAt = this.completedAt;

        new ReadingCompleted(this).publishAfterCommit();
    }

    // ... (@PostPersist 동일)
}