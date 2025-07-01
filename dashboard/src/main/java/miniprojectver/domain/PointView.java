package miniprojectver.domain;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PointView_table")
@Data
public class PointView {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private Integer pointBalance;
}
