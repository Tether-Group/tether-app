package tethergroup.tether.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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


}
