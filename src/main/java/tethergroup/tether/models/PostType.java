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
@Table(name = "post_types")
public class PostType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, name = "type")
    private String type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postType")
    private List<Post> posts;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="post_type_group",
            joinColumns={@JoinColumn(name="post_type_id")},
            inverseJoinColumns={@JoinColumn(name="group_id")}
    )
    private List<Group> groups;

}
