package tethergroup.tether.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "friendships")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "requester")
    private User requester;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "acceptor")
    private User acceptor;

    @Column(name = "is_pending")
    private boolean isPending;

}