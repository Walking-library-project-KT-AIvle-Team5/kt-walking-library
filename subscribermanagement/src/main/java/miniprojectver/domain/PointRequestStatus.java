// src/main/java/miniprojectver/domain/PointRequestStatus.java
package miniprojectver.domain;

public enum PointRequestStatus {
    PENDING,    // 요청 대기 중
    COMPLETED,  // 요청 처리 완료
    FAILED,     // 요청 처리 실패
    CANCELLED   // 요청 취소 (필요시)
}