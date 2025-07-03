package miniprojectver.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional; // Optional 임포트 추가

@RepositoryRestResource(collectionResourceRel = "subscribeManagements", path = "subscribeManagements")
public interface SubscribeManagementRepository
    extends PagingAndSortingRepository<SubscribeManagement, Long> {

    // 사용자 ID(userId)로 SubscribeManagement를 찾는 쿼리 메서드 선언
    Optional<SubscribeManagement> findByUserId(String userId); // <--- 이 줄을 추가
}
