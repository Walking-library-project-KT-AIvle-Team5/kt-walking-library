package miniprojectver.domain;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;


@Entity
@Table(name="Member_table")
public class Member {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String loginId;
    private String password;
    private String name;

    private String role;
    private String status;

    @PostPersist
    public void onPostPersist(){
        MemberSignedUp memberSignedUp = new MemberSignedUp();
        BeanUtils.copyProperties(this, memberSignedUp);
        memberSignedUp.publishAfterCommit();
    }
