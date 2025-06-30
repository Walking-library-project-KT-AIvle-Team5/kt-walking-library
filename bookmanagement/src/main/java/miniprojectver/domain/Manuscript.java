package miniprojectver.domain;

import lombok.Data;
import javax.persistence.*;
import miniprojectver.BookmanagementApplication;

@Entity
@Table(name = "Manuscript_table")
@Data
public class Manuscript {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long manuscriptId;

    private String title;
    private Long authorId;
    private String content;
    private ManuscriptStatus status;
    private Boolean imageRequested;
    private Boolean aiSummaryRequested;
    private String category;
    private String imagePath;
    private String summary;
    private Integer price;

    // 출간 요청 Command Handler
    public void requestPublication() {
        // 상태 변경
        this.status = ManuscriptStatus.PUBLICATION_REQUESTED;

        // 이벤트 발행
        ManuscriptPublicationRequested event = new ManuscriptPublicationRequested(this);
        event.publishAfterCommit();
    }

    // 원고 작성 Command Handler
    public void write(String content) {
        this.content = content;
        this.status = ManuscriptStatus.WRITTEN;

        ManuscriptWritten event = new ManuscriptWritten(this);
        event.publishAfterCommit();
    }

    // 원고 수정 Command Handler
    public void edit(String newContent) {
        this.content = newContent;
        this.status = ManuscriptStatus.EDITED;

        ManuscriptEdited event = new ManuscriptEdited(this);
        event.publishAfterCommit();
    }

    // 원고 삭제 Command Handler
    public void deleteManuscript() {
        this.status = ManuscriptStatus.DELETED;

        ManuscriptDeleted event = new ManuscriptDeleted(this);
        event.publishAfterCommit();
    }

    // AI 표지 생성 요청
    public void requestAiCoverImage() {
        this.imageRequested = true;

        AiCoverImageRequested event = new AiCoverImageRequested(this);
        event.publishAfterCommit();
    }

    // 전자책 분석 요청
    public void requestEbookAnalysis() {
        this.aiSummaryRequested = true;

        EbookAnalysisRequested event = new EbookAnalysisRequested(this);
        event.publishAfterCommit();
    }

    // Repository 접근 메서드
    public static ManuscriptRepository repository() {
        return BookmanagementApplication.applicationContext.getBean(ManuscriptRepository.class);
    }
}
