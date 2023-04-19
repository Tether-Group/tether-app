package tethergroup.tether.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tethergroup.tether.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByOrderByCommentDateDesc();
    List<Comment> findCommentsByGroup_IdOrderByCommentDateDesc(Long groupId);

}
