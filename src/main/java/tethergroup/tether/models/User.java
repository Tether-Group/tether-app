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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false, unique = true, name = "username")
    private String username;

    @Column(length = 100, nullable = false, name = "first_name")
    private String firstName;

    @Column(length = 100, nullable = false, name = "last_name")
    private String lastName;

    @Column(length = 100, nullable = false, name = "email")
    private String email;

    @Column(length = 100, nullable = false, name = "password")
    private String password;

    @Column(length = 1024, name = "bio")
    private String bio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admin")
    private List<Group> groups;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="friends",
            joinColumns={@JoinColumn(name="user_id1")},
            inverseJoinColumns={@JoinColumn(name="user_id2")}
    )
    private List<User> friends;

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
    }
}
