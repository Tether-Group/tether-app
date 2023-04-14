package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tethergroup.tether.models.Membership;
import tethergroup.tether.models.User;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("FROM User u WHERE u.username LIKE %:term%")
    List<User> findLikeUsername(@Param("term") String user);

    @Query(nativeQuery = true,
            value = "select u.*, m.is_pending from memberships m join users u on u.id = m.user_id where m.group_id = :group_id")
    List<User> findByGroupId(@Param("group_id")Long groupId);

    @Query(nativeQuery = true,
            value = "SELECT u.* FROM memberships m JOIN users u ON m.user_id = u.id JOIN groups g ON m.group_id = g.id WHERE m.is_pending = 1 AND g.admin_id = :id")
    Set<User> findUsersFromGroupJoinRequestsForTheAdmin(@Param("id")Long id);
}
