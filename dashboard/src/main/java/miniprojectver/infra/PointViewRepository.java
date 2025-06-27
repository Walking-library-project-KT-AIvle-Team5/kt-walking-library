package miniprojectver.infra;

import java.util.List;
import miniprojectver.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "pointViews",
    path = "pointViews"
)
public interface PointViewRepository
    extends PagingAndSortingRepository<PointView, Long> {}
