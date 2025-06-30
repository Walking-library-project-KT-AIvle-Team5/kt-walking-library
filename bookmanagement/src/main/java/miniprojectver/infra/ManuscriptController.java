package miniprojectver.infra;

import javax.transaction.Transactional;
import miniprojectver.domain.Manuscript;
import miniprojectver.domain.ManuscriptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manuscripts")
@Transactional
public class ManuscriptController {

    @Autowired
    ManuscriptRepository manuscriptRepository;

    /**
     * 출간 요청
     * - 지정된 ID의 원고에 대해 출간을 요청합니다.
     * - Kafka 이벤트 발행과 상태 변경 로직은 도메인 내부의 requestPublication 메소드에서 처리됩니다.
     */
    @PostMapping("/{id}/requestPublication")
    public ResponseEntity<String> requestPublication(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manuscript not found"));
        manuscript.requestPublication();
        manuscriptRepository.save(manuscript);
        return ResponseEntity.ok("Publication requested for manuscript id: " + id);
    }

    /**
     * 원고 작성
     * - 지정된 ID의 원고에 대해 내용을 작성합니다.
     * - POST 본문은 단순 텍스트(content)입니다.
     */
    @PostMapping("/{id}/write")
    public ResponseEntity<String> write(@PathVariable Long id, @RequestBody String content) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manuscript not found"));
        manuscript.write(content);
        manuscriptRepository.save(manuscript);
        return ResponseEntity.ok("Manuscript written: " + id);
    }

    /**
     * 원고 수정
     * - 지정된 ID의 원고 내용을 수정합니다.
     * - POST 본문은 새로운 content 텍스트입니다.
     */
    @PostMapping("/{id}/edit")
    public ResponseEntity<String> edit(@PathVariable Long id, @RequestBody String content) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manuscript not found"));
        manuscript.edit(content);
        manuscriptRepository.save(manuscript);
        return ResponseEntity.ok("Manuscript edited: " + id);
    }

    /**
     * 원고 삭제
     * - 지정된 ID의 원고를 삭제 상태로 변경합니다.
     * - 실제 DB 레코드를 지우지는 않고 soft delete 처리합니다.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manuscript not found"));
        manuscript.deleteManuscript();
        manuscriptRepository.save(manuscript);
        return ResponseEntity.ok("Manuscript deleted: " + id);
    }

    /**
     * AI 표지 이미지 생성 요청
     * - AI 표지 생성 이벤트를 발행합니다.
     */
    @PostMapping("/{id}/requestAiCover")
    public ResponseEntity<String> requestAiCover(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manuscript not found"));
        manuscript.requestAiCoverImage();
        manuscriptRepository.save(manuscript);
        return ResponseEntity.ok("AI cover image requested for manuscript id: " + id);
    }

    /**
     * AI 전자책 분석 요청
     * - AI 전자책 분석 이벤트를 발행합니다.
     */
    @PostMapping("/{id}/requestEbookAnalysis")
    public ResponseEntity<String> requestEbookAnalysis(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manuscript not found"));
        manuscript.requestEbookAnalysis();
        manuscriptRepository.save(manuscript);
        return ResponseEntity.ok("Ebook analysis requested for manuscript id: " + id);
    }
}
