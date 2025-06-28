package miniprojectver.domain;

import java.util.*;
import lombok.*;
import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;

@Data
@ToString
public class BookPublicationChecked extends AbstractEvent {

    private Long id;
    private String title;
    private String summaryContent;
    private String summary;
    private String category;
    private String imagepath;
    private String contents;
    private Integer price;
    private String status;
}
