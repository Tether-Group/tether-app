package tethergroup.tether.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tethergroup.tether.models.*;
import tethergroup.tether.repositories.CommentRepository;
import tethergroup.tether.repositories.GroupRepository;
import tethergroup.tether.repositories.MembershipRepository;
import tethergroup.tether.repositories.PostRepository;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final PostRepository postDao;
    private final GroupRepository groupDao;
    private final CommentRepository commentDao;
    private final MembershipRepository membershipDao;


    @Transactional
    @GetMapping("/")
    public String returnLandingPage(Model model) {
        List<Group> randomGroups = groupDao.randomGroupsLimitFive();
        List<Post> posts = postDao.findByOrderByPostDateDesc();
        model.addAttribute("randoGroups", randomGroups);
        User loggedInUser = new User();
        try {
            loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            System.out.println("User is not logged in");
        }
        model.addAttribute("loggedInUser", loggedInUser);
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

    @GetMapping("/about")
    public String returnAboutPage() {return "about";}

    @GetMapping("/error")
    public String returnErrorPage(){return "error";}
}
