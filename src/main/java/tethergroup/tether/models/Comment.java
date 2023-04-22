package tethergroup.tether.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User commenter;

    @ManyToOne
    @JoinColumn (name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn (name = "group_id")
    private Group group;

    @Column(name = "content")
    private String content;

    @Column(name = "comment_date")
    private Timestamp commentDate;

    public String commentDateToString() {
        PrettyTime p = new PrettyTime();
        return "" + p.format(this.commentDate);
    }


}
