package tethergroup.tether.models;

import jakarta.persistence.*;
import lombok.*;

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
    private List<Group> groupsWhereUserIsAdmin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requester")
    private List<Friendship> friendshipsRequester;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "acceptor")
    private List<Friendship> friendshipsAcceptor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Membership> memberships;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commenter")
    private List<Comment> comments;

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
        firstName = copy.firstName;
        lastName = copy.lastName;
        bio = copy.bio;
        groupsWhereUserIsAdmin = copy.groupsWhereUserIsAdmin;
        friendshipsRequester = copy.friendshipsRequester;
        friendshipsAcceptor = copy.friendshipsAcceptor;
        memberships = copy.memberships;
    }

    @Override
    public String toString() {
        return "@" + this.username;
    }
}
