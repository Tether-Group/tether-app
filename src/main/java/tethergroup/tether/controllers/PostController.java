package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class PostController {

    @GetMapping("/posts")
    public String posts() {
        return "index";
    }

    @GetMapping("/search")
    public String searchResults() {
        return "posts/search-results";
    }

    @GetMapping("/post/create")
    public String createPost(){
        return "posts/create-post";
    }
}
