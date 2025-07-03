// package miniprojectver.controller;

// import lombok.RequiredArgsConstructor;
// import miniprojectver.domain.Book;
// import miniprojectver.domain.BookRepository;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/books")
// @RequiredArgsConstructor
// public class BookController {

//     private final BookRepository bookRepository;

//     // 도서 목록 전체 조회
//     @GetMapping
//     public ResponseEntity<List<Book>> getAllBooks() {
//         List<Book> books = bookRepository.findAll();
//         return ResponseEntity.ok(books);
//     }

//     // 도서 단건 조회 (선택)
//     @GetMapping("/{id}")
//     public ResponseEntity<Book> getBookById(@PathVariable Long id) {
//         return bookRepository.findById(id)
//                 .map(ResponseEntity::ok)
//                 .orElse(ResponseEntity.notFound().build());
//     }
// }
