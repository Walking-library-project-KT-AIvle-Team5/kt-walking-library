package miniprojectver.domain;

import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
public class ManuscriptPublicationRequested extends AbstractEvent {

    private Long manuscriptId;
    private String title;
    private Long authorId;
    private String content;
    private Object status;
    private String category;
    private String imagePath;
    private String summary;
    private Integer price;
}
