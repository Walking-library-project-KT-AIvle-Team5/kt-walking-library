package miniprojectver.domain;

import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;
import java.util.*;
import lombok.*;
import java.time.LocalDate;


//<<< DDD / Domain Event
@Data
@ToString
public class ManuscriptPublicationRequested extends AbstractEvent {

    private Long manuscriptId;
    private String title;
    private Long authorId;
    private String content;
    private New Enumeration status;
    private String category;
    private String imagePath;
    private String summary;
    private Integer price;

    public ManuscriptPublicationRequested(Manuscript aggregate){
        super(aggregate);
    }
    public ManuscriptPublicationRequested(){
        super();
    }
}
//>>> DDD / Domain Event