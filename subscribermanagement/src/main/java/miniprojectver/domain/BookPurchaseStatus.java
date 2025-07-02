// src/main/java/miniprojectver/domain/BookPurchaseStatus.java
package miniprojectver.domain;

public enum BookPurchaseStatus {
    PENDING,   // 구매 요청 대기 중 (초기 상태)
    COMPLETED, // 구매 완료됨
    FAILED     // 구매 실패됨
    // 필요한 경우, 'CANCELED_BY_USER', 'REFUNDED' 등 추가 가능
}