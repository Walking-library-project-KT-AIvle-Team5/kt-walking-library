package miniprojectver.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import miniprojectver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping("/bookPublications")
@Transactional
public class BookPublicationController {

    @Autowired
    BookPublicationRepository bookPublicationRepository;

    // 출간 심사 승인 또는 반려 처리
    @PutMapping("/{id}/review")
    public String reviewBookPublication(@PathVariable Long id, @RequestParam String decision) {

        Optional<BookPublication> optionalBook = bookPublicationRepository.findById(id);
        if (optionalBook.isPresent()) {
            BookPublication book = optionalBook.get();

            if ("approve".equalsIgnoreCase(decision)) {
                book.setStatus("APPROVED");
            } else if ("reject".equalsIgnoreCase(decision)) {
                book.setStatus("REJECTED");
            } else {
                return "Decision must be 'approve' or 'reject'.";
            }

            bookPublicationRepository.save(book); // @PostUpdate 이벤트 트리거
            return "Book review processed.";
        } else {
            return "Book not found.";
        }
    }

    // 도서 내려짐 처리
    @PutMapping("/{id}/unpublish")
    public String unpublishBook(@PathVariable Long id) {

        Optional<BookPublication> optionalBook = bookPublicationRepository.findById(id);
        if (optionalBook.isPresent()) {
            BookPublication book = optionalBook.get();
            if ("UNPUBLISHED".equals(book.getStatus())) {
                return "Book is already unpublished.";
            }

            book.setStatus("UNPUBLISHED");

            bookPublicationRepository.save(book); // @PostUpdate 이벤트 트리거
            return "Book unpublished.";
        } else {
            return "Book not found.";
        }
    }
}
//>>> Clean Arch / Inbound Adaptor