package miniprojectver.domain;

import miniprojectver.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;


//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "subscribeManagements",
    path = "subscribeManagements"
)
public interface SubscribeManagementRepository
    extends PagingAndSortingRepository<SubscribeManagement, Long> {

            // ✅ userId로 구독자 정보를 조회하기 위한 메서드
    Optional<SubscribeManagement> findByUserId(String userId);
    }
