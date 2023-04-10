package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tethergroup.tether.models.Group;
import tethergroup.tether.models.Post;
import tethergroup.tether.repositories.PostRepository;
import tethergroup.tether.repositories.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostRepository postDao;

    @GetMapping("/posts")
    public String posts() {
        return "index";
    }

    @GetMapping("/search")
    public String searchResults() {
        return "posts/search-results";
    }

    @GetMapping("/search")
    public String postsSearched(Model model){
        List<Post> searchedPosts = postDao.postsSearched();
        model.addAttribute("searchedPosts", searchedPosts);
//        TODO: Insert display location for searched posts below
        return "users/index";
    }
}
