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

    @OneToOne
    private User admin;
//    many to one

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="post_type_group",
            joinColumns={@JoinColumn(name="group_id")},
            inverseJoinColumns={@JoinColumn(name="post_type_id")}
    )
    private List<PostType> postTypes;
}
