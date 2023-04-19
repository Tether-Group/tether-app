package tethergroup.tether.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tethergroup.tether.models.Comment;
import tethergroup.tether.models.Group;
import tethergroup.tether.models.Post;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.CommentRepository;
import tethergroup.tether.repositories.GroupRepository;
import tethergroup.tether.repositories.PostRepository;

import java.util.List;

@Controller
public class HomeController {

    private final PostRepository postDao;
    private final GroupRepository groupDao;
    private final CommentRepository commentDao;

    public HomeController(PostRepository postDao, GroupRepository groupDao, CommentRepository commentDao) {
        this.postDao = postDao;
        this.groupDao = groupDao;
        this.commentDao = commentDao;
    }

    @GetMapping("/")
    public String returnLandingPage(Model model) {
        List<Group> randomGroups = groupDao.randomGroupsLimitFive();
        model.addAttribute("randoGroups", randomGroups);
        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("loggedInUser", loggedInUser);
        } catch (Exception e) {
            System.out.println("User is not logged in");
        }
        List<Post> posts = postDao.findByOrderByPostDateDesc();
        List<Group> groups = groupDao.findAll();
        List<Comment> comments = commentDao.findAll();
        for (Comment comment : comments) {
            System.out.println(comment.getPost().getId());
        }
        model.addAttribute("posts",posts);
        model.addAttribute("groups",groups);
        model.addAttribute("comments", comments);
        return "index";
    }

//    @GetMapping("/")

    @GetMapping("/about")
    public String returnAboutPage() {return "about";}

    @GetMapping("/error")
    public String returnErrorPage(){return "error";}
}
