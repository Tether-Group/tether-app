package tethergroup.tether.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, name = "name")
    private String name;

    @Column(length = 1024, name = "description")
    private String description;

    @Column(nullable = false, name = "is_private")
    private boolean isPrivate;

    @ManyToOne
    @JoinColumn (name = "admin_id")
    private User admin;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(
            name="post_type_group",
            joinColumns={@JoinColumn(name="group_id")},
            inverseJoinColumns={@JoinColumn(name="post_type_id")}
    )
    private List<PostType> postTypes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private List<Membership> memberships;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private List<Comment> comments;

    @Column(name = "group_photo_url")
    private String groupPhotoURL;

    public String toString() {
        return "" + this.name;
    }
}
