package miniprojectver.dto;

import lombok.Data;
import miniprojectver.domain.Manuscript;
import miniprojectver.domain.ManuscriptStatus;

@Data
public class ManuscriptResponseDto {
    private Long manuscriptId;
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

    public static ManuscriptResponseDto fromEntity(Manuscript m) {
        ManuscriptResponseDto dto = new ManuscriptResponseDto();
        dto.setManuscriptId(m.getManuscriptId());
        dto.setTitle(m.getTitle());
        dto.setAuthorId(m.getAuthorId());
        dto.setContent(m.getContent());
        dto.setCategory(m.getCategory());
        dto.setStatus(m.getStatus());
        dto.setImageRequested(m.getImageRequested());
        dto.setAiSummaryRequested(m.getAiSummaryRequested());
        dto.setImagePath(m.getImagePath());
        dto.setSummary(m.getSummary());
        dto.setPrice(m.getPrice());
        return dto;
    }
}
