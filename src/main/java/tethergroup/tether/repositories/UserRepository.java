package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tethergroup.tether.models.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("FROM User u WHERE u.username LIKE %:term%")
    List<User> findLikeUsername(@Param("term") String user);

//    List<User> findByGroups_id(Long id);

//    @Query("select t from Test t join User u where u.username = :username")

//    @Query("select u.username from User u join Group g where g.id = :group_id")
//    List<User> findByGroupId(@Param("group_id")Long groupId);
//
    // select * from user_group ug join users u on u.id = ug.user_id where ug.group_id = 3;

    @Query(value = "select * from user_group ug join users u on u.id = ug.user_id where ug.group_id = :group_id",
    nativeQuery = true)
    List<User> findByGroupId(@Param("group_id")Long groupId);

}
