package miniprojectver.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "PointView_table")
@Data
public class PointView {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
}
