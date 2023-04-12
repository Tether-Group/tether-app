package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import tethergroup.tether.models.Group;
import tethergroup.tether.models.Post;
import tethergroup.tether.repositories.GroupRepository;
import tethergroup.tether.repositories.PostRepository;
import tethergroup.tether.repositories.UserRepository;

import java.util.List;

@Controller
public class HomeController {

    private final PostRepository postDao;
    private final GroupRepository groupDao;

    public HomeController(PostRepository postDao, GroupRepository groupDao) {
        this.postDao = postDao;
        this.groupDao = groupDao;
    }

    @GetMapping("/")
    public String returnLandingPage(Model model) {
        List<Group> randomGroups = groupDao.randomGroups();
        model.addAttribute("randoGroups", randomGroups);

        List<Post> posts = postDao.findAll();
        List<Group> groups = groupDao.findAll();
        model.addAttribute("posts",posts);
        model.addAttribute("groups",groups);
        return "index";
    }
}
