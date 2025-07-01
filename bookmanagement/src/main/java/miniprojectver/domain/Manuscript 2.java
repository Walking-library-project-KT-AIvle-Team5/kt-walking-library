package miniprojectver.domain;

import miniprojectver.domain.ManuscriptPublicationRequested;
import miniprojectver.domain.ManuscriptWritten;
import miniprojectver.domain.ManuscriptEdited;
import miniprojectver.domain.ManuscriptDeleted;
import miniprojectver.domain.AiCoverImageRequested;
import miniprojectver.domain.EbookAnalysisRequested;
import miniprojectver.BookmanagementApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;


@Entity
@Table(name="Manuscript_table")
@Data

//<<< DDD / Aggregate Root
public class Manuscript  {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
private Long manuscriptId;    
    
    
private String title;    
    
    
private Long authorId;    
    
    
private String content;    
    
    
private New Enumeration status;    
    
    
private Boolean imageRequested;    
    
    
private Boolean aiSummaryRequested;    
    
    
private String category;    
    
    
private String imagePath;    
    
    
private String summary;    
    
    
private Integer price;

    @PostPersist
    public void onPostPersist(){


        ManuscriptPublicationRequested manuscriptPublicationRequested = new ManuscriptPublicationRequested(this);
        manuscriptPublicationRequested.publishAfterCommit();



        ManuscriptWritten manuscriptWritten = new ManuscriptWritten(this);
        manuscriptWritten.publishAfterCommit();



        ManuscriptEdited manuscriptEdited = new ManuscriptEdited(this);
        manuscriptEdited.publishAfterCommit();



        ManuscriptDeleted manuscriptDeleted = new ManuscriptDeleted(this);
        manuscriptDeleted.publishAfterCommit();



        AiCoverImageRequested aiCoverImageRequested = new AiCoverImageRequested(this);
        aiCoverImageRequested.publishAfterCommit();



        EbookAnalysisRequested ebookAnalysisRequested = new EbookAnalysisRequested(this);
        ebookAnalysisRequested.publishAfterCommit();

    
    }

    public static ManuscriptRepository repository(){
        ManuscriptRepository manuscriptRepository = BookmanagementApplication.applicationContext.getBean(ManuscriptRepository.class);
        return manuscriptRepository;
    }






}
//>>> DDD / Aggregate Root
