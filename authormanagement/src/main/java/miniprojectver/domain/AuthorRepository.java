package miniprojectver.domain;

import miniprojectver.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;


//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "authors", path = "authors")
public interface AuthorRepository
    extends PagingAndSortingRepository<Author, Long> {
        // 기존 역할(role) 검색 메서드
        List<Author> findByAuthorRole(String authorRole);

        // 정확 일치 이름 검색
        List<Author> findByAuthorName(String authorName);

        // 부분 일치(contains) 이름 검색
        List<Author> findByAuthorNameContaining(String keyword);
    }
