package miniprojectver.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "manuscripts")
public interface ManuscriptRepository extends PagingAndSortingRepository<Manuscript, Long> {
    // 필요 시 커스텀 쿼리 추가
}
