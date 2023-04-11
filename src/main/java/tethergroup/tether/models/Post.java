package tethergroup.tether.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Timestamp;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false, name = "header")
    private String header;

    @Column(nullable = false, name = "body")
    private String body;

    @Column(name = "post_date")
    private Timestamp postDate;

    @Column(name = "price", nullable = true)
    private Integer postPrice;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(length = 256, name = "event_address")
    private String eventAddress;

    @ManyToOne
    @JoinColumn (name = "post_type_id")
    private PostType postType;

    @OneToOne
    private User user;

    @OneToOne
    private Group group;

    public String postDateToString() {
        PrettyTime p = new PrettyTime();
        return "" + p.format(this.postDate);
    }
}
