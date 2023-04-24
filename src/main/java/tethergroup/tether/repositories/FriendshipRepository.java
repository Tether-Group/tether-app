package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tethergroup.tether.models.Friendship;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Friendship findByRequester_IdAndAcceptor_Id(Long requesterId, Long acceptorId);

    @Query(nativeQuery = true,
    value = "select * from friendships f where acceptor = :user_id or requester = :user_id and is_pending = 0")
    List<Friendship> getFriendshipsOfUser(@Param("user_id") Long userId);

    @Query(nativeQuery = true,
            value = "SELECT count(id) FROM friendships f WHERE f.acceptor = :user_id AND is_pending = 1")
    Long getCountOfFriendRequestsForLoggedInUser(@Param("user_id") Long loggedInUserId);

    @Query(nativeQuery = true,
            value = "SELECT * FROM friendships f WHERE f.acceptor = :user_id AND is_pending = 1")
    List<Friendship> getFriendshipRequestsForLoggedInUser(@Param("user_id") Long loggedInUserId);

}
