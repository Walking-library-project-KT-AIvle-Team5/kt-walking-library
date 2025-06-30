package miniprojectver.infra;

import java.util.List;
import miniprojectver.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "fetchSubscriberLists",
    path = "fetchSubscriberLists"
)
public interface FetchSubscriberListRepository
    extends PagingAndSortingRepository<FetchSubscriberList, Long> {
    List<FetchSubscriberList> findBySubscriptionId(Long subscriptionId);
}
