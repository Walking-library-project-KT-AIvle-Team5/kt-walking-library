package miniprojectver.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Optional;

// PDF 47~48페이지의 Repository 구현 예시를 참고합니다.
// PagingAndSortingRepository<Member, Long> 의 의미:
// "Member 엔티티를 관리하고, 기본 키(ID)의 타입은 Long이다."
public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {

    // 나중에 로그인 기능을 구현할 때, 아이디(loginId)로 회원을 찾아야 합니다.
    // 이 메서드를 미리 만들어두면, Spring Data JPA가 메서드 이름을 분석해서
    // "loginId 필드로 Member를 찾는" SQL 쿼리를 자동으로 생성해줍니다.
    Optional<Member> findByLoginId(String loginId);

}