package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tethergroup.tether.models.Membership;
import tethergroup.tether.models.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("FROM User u WHERE u.username LIKE %:term%")
    List<User> findLikeUsername(@Param("term") String user);

    @Query(value = "select u.*, m.is_pending from memberships m join users u on u.id = m.user_id where m.group_id = :group_id",
    nativeQuery = true)
    List<User> findByGroupId(@Param("group_id")Long groupId);
}
