package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tethergroup.tether.models.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("FROM Post p WHERE p.header LIKE %:term% OR p.body LIKE %:term% OR p.postType.type LIKE %:term%")
    List<Post> findLikePostNameOrHeaderOrBody(@Param("term") String post);

    List<Post> findByOrderByPostDateDesc();

    List<Post> findByGroup_IdOrderByPostDateDesc(Long groupId);

    @Query(nativeQuery = true,
            value = "SELECT DISTINCT p.* FROM posts p INNER JOIN users u ON u.id = p.user_id INNER JOIN memberships m ON m.user_id = u.id INNER JOIN groups g ON g.id = m.group_id WHERE u.id = :user_id")
    List<Post> getAllPostsThatLoggedInUserUserCanCommentOn(@Param("user_id") Long userId);

    List<Post> findPostsByUser_IdOrderByPostDateDesc(Long postId);

}
