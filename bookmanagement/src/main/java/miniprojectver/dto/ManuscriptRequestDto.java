package miniprojectver.dto;

import lombok.Data;
import miniprojectver.domain.Manuscript;
import miniprojectver.domain.ManuscriptStatus;

@Data
public class ManuscriptRequestDto {
    private String title;
    private Long authorId;
    private String content;
    private String category;
    private ManuscriptStatus status;
    private Boolean imageRequested;
    private Boolean aiSummaryRequested;
    private String imagePath;
    private String summary;
    private Integer price;

    public Manuscript toEntity() {
        Manuscript m = new Manuscript();
        m.setTitle(title);
        m.setAuthorId(authorId);
        m.setContent(content);
        m.setCategory(category);
        m.setStatus(status);
        m.setImageRequested(imageRequested);
        m.setAiSummaryRequested(aiSummaryRequested);
        m.setImagePath(imagePath);
        m.setSummary(summary);
        m.setPrice(price);
        return m;
    }
}
