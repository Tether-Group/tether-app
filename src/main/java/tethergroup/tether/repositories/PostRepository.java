package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tethergroup.tether.models.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM `posts` WHERE  ${global-search} = header")
    List<Post> postsSearched();
}
