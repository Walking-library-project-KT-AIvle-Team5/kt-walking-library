package miniprojectver.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ManuscriptRepository extends PagingAndSortingRepository<Manuscript, Long> {
    // 필요 시 커스텀 쿼리 추가
}