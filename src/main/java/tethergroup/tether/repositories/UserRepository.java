package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tethergroup.tether.models.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT * FROM `users` WHERE ${global-search} = username")
    List<User> usersSearched();
}
