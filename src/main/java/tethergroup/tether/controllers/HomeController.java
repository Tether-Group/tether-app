package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import tethergroup.tether.models.Post;
import tethergroup.tether.repositories.PostRepository;
import tethergroup.tether.repositories.UserRepository;

import java.util.List;

@Controller
public class HomeController {

    private PostRepository postDao;

    public HomeController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/")
    public String returnLandingPage(Model model) {
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts",posts);
        return "index";}
}
