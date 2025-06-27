package miniprojectver.domain;

import miniprojectver.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "memberManagements",
    path = "memberManagements"
)
public interface MemberManagementRepository
    extends PagingAndSortingRepository<MemberManagement, Long> {}
