package miniprojectver.domain;

import lombok.*;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
public class ManuscriptPublicationRequested extends AbstractEvent {

    private Long manuscriptId;
    private String title;
    private Long authorId;
    private String content;
    private ManuscriptStatus status;  
    private String category;
    private String imagePath;
    private String summary;
    private String summaryContent;
    private Integer price;
}
