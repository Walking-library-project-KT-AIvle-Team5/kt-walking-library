package miniprojectver.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    // 필요 시 커스텀 쿼리 메서드 추가
}
