package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tethergroup.tether.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
