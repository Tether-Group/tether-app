package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tethergroup.tether.models.*;
import tethergroup.tether.repositories.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentRepository commentDao;
    private final UserRepository userDao;
    private final PostRepository postDao;
    private final GroupRepository groupDao;
    private final MembershipRepository membersipDao;

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
        Timestamp timestamp = new Timestamp((new Date()).getTime());
        newComment.setCommentDate(timestamp);
        commentDao.save(newComment);
        return "redirect:/group/" + groupId;
    }

    @PostMapping("/comment/edit")
    public String editPost(@RequestParam("commentId") Long commentId, @RequestParam("commentContent") String commentContent) {
        Comment comment = commentDao.findById(commentId).get();
        Long groupId = comment.getGroup().getId();
        comment.setContent(commentContent);
        commentDao.save(comment);
        return "redirect:/group/" + groupId;
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam("commentId") Long commentId) {
        Comment comment = commentDao.findById(commentId).get();
        Long groupId = comment.getGroup().getId();
        commentDao.delete(comment);
        return "redirect:/group/" + groupId;
    }

    @GetMapping(value = "/verifyAddComments", produces = "application/json")
    @ResponseBody
    public List<Long> getPostsWhereUserIsAbleToComment() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser == null) {
            return null;
        }

        List<Post> posts = postDao.findAll();
        List<Long> idsOfPostsThatLoggedInUserCanCommentOn = new ArrayList<>();

        for (Post post : posts) {
            if (post.getGroup().getAdmin().getId() == loggedInUser.getId()) {
                idsOfPostsThatLoggedInUserCanCommentOn.add(post.getId());
            } else {
                List<Membership> membershipsForGroupOfPost = membersipDao.findMembershipsByGroup_Id(post.getGroup().getId());
                for (Membership membership : membershipsForGroupOfPost) {
                    if (membership.getUser().getId() == loggedInUser.getId()) {
                        idsOfPostsThatLoggedInUserCanCommentOn.add(post.getId());
                        break;
                    }
                }
            }
        }
        return idsOfPostsThatLoggedInUserCanCommentOn;
    }

}
