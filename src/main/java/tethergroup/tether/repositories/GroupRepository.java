package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tethergroup.tether.models.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
