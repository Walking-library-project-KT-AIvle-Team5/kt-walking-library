package miniprojectver.domain;

import miniprojectver.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "bookPublications",
    path = "bookPublications"
)
public interface BookPublicationRepository
    extends PagingAndSortingRepository<BookPublication, Long> {}
