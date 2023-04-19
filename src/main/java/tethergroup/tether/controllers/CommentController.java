package tethergroup.tether.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tethergroup.tether.models.Comment;
import tethergroup.tether.models.Group;
import tethergroup.tether.models.Post;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.*;

@Controller
public class CommentController {

    private final CommentRepository commentDao;
    private final UserRepository userDao;
    private final PostRepository postDao;
    private final GroupRepository groupDao;

    CommentController(UserRepository userDao, PostRepository postDao, CommentRepository commentDao, GroupRepository groupDao) {
        this.commentDao = commentDao;
        this.userDao = userDao;
        this.postDao = postDao;
        this.groupDao = groupDao;
    }

    @PostMapping("/{postId}/comment/add")
    public String addComment(@PathVariable Long postId, @RequestParam Long groupId, @RequestParam String commentContent) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User commenter = userDao.findById(loggedInUser.getId()).get();
        Post post = postDao.findById(postId).get();
        Group group = groupDao.findById(groupId).get();
        Comment newComment = new Comment();
        newComment.setPost(post);
        newComment.setCommenter(commenter);
        newComment.setGroup(group);
        newComment.setContent(commentContent);
        commentDao.save(newComment);
        return "redirect:/";
    }

    @PostMapping("/comment/edit")
    public String editPost(@RequestParam("commentId") Long commentId, @RequestParam("commentContent") String commentContent) {
        Comment comment = commentDao.findById(commentId).get();
        comment.setContent(commentContent);
        commentDao.save(comment);
        return "redirect:/";
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam("commentId") Long commentId) {
        Comment comment = commentDao.findById(commentId).get();
        commentDao.delete(comment);
        return "redirect:/";
    }

}
