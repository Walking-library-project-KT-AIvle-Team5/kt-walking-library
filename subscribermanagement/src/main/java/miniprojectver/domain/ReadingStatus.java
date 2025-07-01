// src/main/java/miniprojectver/domain/ReadingStatus.java
package miniprojectver.domain;

public enum ReadingStatus {
    STARTED,     // 독서 시작됨
    IN_PROGRESS, // 독서 진행 중
    COMPLETED,   // 독서 완료됨
    PAUSED,      // 독서 일시 중지됨 (선택 사항)
    CANCELLED    // 독서 취소됨 (선택 사항)
}