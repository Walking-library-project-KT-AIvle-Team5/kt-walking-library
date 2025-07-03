package miniprojectver.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional; // Optional 임포트
import java.util.List;     // List 임포트 추가 (findByUserIdAndStatus가 List를 반환하기 때문)

@RepositoryRestResource(collectionResourceRel = "subscribeManagements", path = "subscribeManagements")
public interface SubscribeManagementRepository
    extends PagingAndSortingRepository<SubscribeManagement, Long> {

    // 사용자 ID(userId)로 SubscribeManagement를 찾는 쿼리 메서드 (기존에 있던 것)
    Optional<SubscribeManagement> findByUserId(String userId);

    // **** 이 메서드를 추가해야 합니다! ****
    // 사용자 ID(userId)와 상태(status)로 SubscribeManagement 목록을 찾는 쿼리 메서드
    List<SubscribeManagement> findByUserIdAndStatus(String userId, String status);
}
