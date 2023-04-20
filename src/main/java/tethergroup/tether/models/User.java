package tethergroup.tether.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(length = 30, name = "reset_password_token")
    private String resetPasswordToken;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admin")
    private List<Group> groupsWhereUserIsAdmin;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requester")
    private List<Friendship> friendshipsRequester;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "acceptor")
    private List<Friendship> friendshipsAcceptor;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Membership> memberships;

    @JsonIgnore
    @ToString.Exclude
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
        resetPasswordToken = copy.resetPasswordToken;
        groupsWhereUserIsAdmin = copy.groupsWhereUserIsAdmin;
        friendshipsRequester = copy.friendshipsRequester;
        friendshipsAcceptor = copy.friendshipsAcceptor;
        memberships = copy.memberships;
    }

    @Override
    @JsonIgnore
    public String toString() {
        return "@" + this.username;
    }


}
