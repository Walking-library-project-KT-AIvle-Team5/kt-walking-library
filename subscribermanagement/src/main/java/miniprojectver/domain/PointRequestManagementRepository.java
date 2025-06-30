package miniprojectver.domain;

import miniprojectver.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "pointRequestManagements",
    path = "pointRequestManagements"
)
public interface PointRequestManagementRepository
    extends PagingAndSortingRepository<PointRequestManagement, Long> {}
