package miniprojectver.infra;

import java.util.List;
import miniprojectver.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "authorStatistics",
    path = "authorStatistics"
)
public interface AuthorStatisticsRepository
    extends PagingAndSortingRepository<AuthorStatistics, Long> {}
