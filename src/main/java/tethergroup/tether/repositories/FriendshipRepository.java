package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tethergroup.tether.models.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Friendship findByRequester_IdAndAcceptor_Id(Long requesterId, Long acceptorId);
}
