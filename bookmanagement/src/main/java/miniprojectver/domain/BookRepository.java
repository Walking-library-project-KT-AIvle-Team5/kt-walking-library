package miniprojectver.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    // 필요 시 커스텀 쿼리 메서드 추가
}
