package miniprojectver.domain;

import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;
import java.util.*;
import lombok.*;
import java.time.LocalDate;


//<<< DDD / Domain Event
@Data
@ToString
public class ManuscriptEdited extends AbstractEvent {

    private Long manuscriptId;
    private String title;
    private Long authorId;
    private String content;
    private New Enumeration status;

    public ManuscriptEdited(Manuscript aggregate){
        super(aggregate);
    }
    public ManuscriptEdited(){
        super();
    }
}
//>>> DDD / Domain Event