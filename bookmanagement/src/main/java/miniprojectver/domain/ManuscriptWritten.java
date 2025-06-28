package miniprojectver.domain;

import miniprojectver.domain.*;
import miniprojectver.infra.AbstractEvent;
import java.util.*;
import lombok.*;
import java.time.LocalDate;


//<<< DDD / Domain Event
@Data
@ToString
public class ManuscriptWritten extends AbstractEvent {

    private Long manuscriptId;
    private String title;
    private Long authorId;
    private String content;
    private New Enumeration status;

    public ManuscriptWritten(Manuscript aggregate){
        super(aggregate);
    }
    public ManuscriptWritten(){
        super();
    }
}
//>>> DDD / Domain Event