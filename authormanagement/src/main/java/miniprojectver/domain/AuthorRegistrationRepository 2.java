package miniprojectver.domain;

import miniprojectver.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "authorRegistrations",
    path = "authorRegistrations"
)
public interface AuthorRegistrationRepository
    extends PagingAndSortingRepository<AuthorRegistration, Long> {}
