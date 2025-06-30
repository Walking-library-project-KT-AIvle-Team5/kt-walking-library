package miniprojectver.domain;

import miniprojectver.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "bookPurchaseManagements",
    path = "bookPurchaseManagements"
)
public interface BookPurchaseManagementRepository
    extends PagingAndSortingRepository<BookPurchaseManagement, Long> {}
