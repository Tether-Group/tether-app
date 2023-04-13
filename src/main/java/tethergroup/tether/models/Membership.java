package tethergroup.tether.models;

import jakarta.persistence.*;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "is_pending")
    private boolean isPending;

}
