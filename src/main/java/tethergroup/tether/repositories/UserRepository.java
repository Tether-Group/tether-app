package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tethergroup.tether.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
