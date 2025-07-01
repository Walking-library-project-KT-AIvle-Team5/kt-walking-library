package miniprojectver.infra;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import miniprojectver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;


//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/authors")
@Transactional
public class AuthorController {
    @Autowired
    AuthorRepository authorRepository;

    // 저자 등록 (POST /authors)
    @PostMapping
    public ResponseEntity<Author> createAuthor(
            @RequestBody Author author,
            @RequestHeader("X-Member-Id") Long memberId) {
        author.setMemberId(memberId);
        author.setRequestedDate(new Date());
        author.setAuthorRole("PENDING");
        Author saved = authorRepository.save(author);
        return ResponseEntity.ok(saved);
    }

    // 저자 전체 조회 (GET /authors)
    @GetMapping
    public ResponseEntity<Iterable<Author>> getAuthors() {
        return ResponseEntity.ok(authorRepository.findAll());
    }

    // 저자 단건 조회 (GET /authors/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable("id") Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 이름으로 정확 일치 검색 (GET /authors/search?name={name})
    @GetMapping("/search")
    public ResponseEntity<List<Author>> searchByName(@RequestParam("name") String name) {
        List<Author> list = authorRepository.findByAuthorName(name);
        return ResponseEntity.ok(list);
    }

    // 이름 부분 일치 검색 (GET /authors/search/contains?keyword={keyword})
    @GetMapping("/search/contains")
    public ResponseEntity<List<Author>> searchByNameContains(@RequestParam("keyword") String keyword) {
        List<Author> list = authorRepository.findByAuthorNameContaining(keyword);
        return ResponseEntity.ok(list);
    }

    // 등록 승인 (PUT /authors/{id}/approve)
    @PutMapping("/{id}/approve")
    public ResponseEntity<Author> approveAuthor(@PathVariable("id") Long id) {
        Optional<Author> opt = authorRepository.findById(id);
        if(!opt.isPresent()) return ResponseEntity.notFound().build();

        Author author = opt.get();
        author.setCheckedDate(new Date());
        author.setAuthorRole("APPROVED");
        author.setDenialReason(null); // 거절사유 초기화
        authorRepository.save(author);
        // @PreUpdate에서 이벤트가 발행됨
        return ResponseEntity.ok(author);
    }

    // 등록 거절 (PUT /authors/{id}/deny)
    @PutMapping("/{id}/deny")
    public ResponseEntity<Author> denyAuthor(
            @PathVariable Long id,
            @RequestBody Map<String, String> body, 
            @RequestHeader("X-Admin-Id") Long adminId
    ) {
        String reason = body.get("denialReason");
        Optional<Author> opt = authorRepository.findById(id);
        if (!opt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Author author = opt.get();
        author.setCheckedDate(new Date());
        author.setAuthorRole("DENIED");
        author.setDenialReason(reason);
        authorRepository.save(author);
        return ResponseEntity.ok(author);
    }

    // 저자 삭제 (DELETE /authors/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") Long id) {
        if(!authorRepository.existsById(id)) return ResponseEntity.notFound().build();
        authorRepository.deleteById(id);
        // @PreRemove에서 이벤트가 발행됨
        return ResponseEntity.ok().build();
    }
    
}
//>>> Clean Arch / Inbound Adaptor
