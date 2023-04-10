package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tethergroup.tether.models.PostType;

public interface PostTypeRepository extends JpaRepository<PostType, Long> {
}
