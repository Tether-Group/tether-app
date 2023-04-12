package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tethergroup.tether.models.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("FROM Post p WHERE p.header LIKE %:term% OR p.body LIKE %:term% OR p.postType.type LIKE %:term%")
    List<Post> findLikePostNameOrHeaderOrBody(@Param("term") String post);
}
