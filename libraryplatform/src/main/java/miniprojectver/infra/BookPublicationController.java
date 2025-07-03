package miniprojectver.infra;

import java.util.Optional;
import javax.transaction.Transactional;
import miniprojectver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookPublications")
@Transactional
public class BookPublicationController {

    @Autowired
    private BookPublicationRepository bookPublicationRepository;

    // 전체 도서 목록 조회
    @GetMapping("/list")
    public Iterable<BookPublication> listAll() {
        return bookPublicationRepository.findAll();
    }

    // 출간 심사 처리 (승인 또는 반려)
    @PutMapping("/{id}/review")
    public String reviewBookPublication(@PathVariable Long id, @RequestParam String decision) {
        System.out.println(">>> reviewBookPublication 진입: id=" + id + ", decision=" + decision);
        Optional<BookPublication> optionalBook = bookPublicationRepository.findById(id);

        if (optionalBook.isEmpty()) {
            return "Book not found.";
        }

        BookPublication book = optionalBook.get();

        switch (decision.toLowerCase()) {
            case "approve":
                book.setStatus(BookStatus.PUBLISHED);
                break;
            case "reject":
                book.setStatus(BookStatus.ARCHIVED);
                break;
            default:
                return "Invalid decision. Must be 'approve' or 'reject'.";
        }

        try {
            bookPublicationRepository.save(book);
            return "Book review processed.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // 도서 내려짐 처리
    @PutMapping("/{id}/unpublish")
    public String unpublishBook(@PathVariable Long id) {
        Optional<BookPublication> optionalBook = bookPublicationRepository.findById(id);

        if (optionalBook.isEmpty()) {
            return "Book not found.";
        }

        BookPublication book = optionalBook.get();

        if (BookStatus.ARCHIVED.equals(book.getStatus())) {
            return "Book is already unpublished.";
        }

        book.setStatus(BookStatus.ARCHIVED);
        bookPublicationRepository.save(book);
        return "Book unpublished.";
    }
}
