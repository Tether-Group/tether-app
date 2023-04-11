package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tethergroup.tether.models.Post;
import tethergroup.tether.repositories.GroupRepository;
import tethergroup.tether.repositories.PostRepository;
import tethergroup.tether.repositories.PostTypeRepository;
import tethergroup.tether.repositories.UserRepository;

import java.sql.Timestamp;
import java.util.Date;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final GroupRepository grouDao;
    private final PostTypeRepository postTypeDao;

    @GetMapping("/posts")
    public String posts() {
        return "index";
    }

    @GetMapping("/post/create")
    public String createPost(Model model){
        model.addAttribute("post", new Post());
        return "posts/create-post";
    }


    @PostMapping("/post/create/text")
    public String createPost(
                             @RequestParam("header") String title,
                             @RequestParam("body") String body) {
        Post post = new Post();
        post.setUser(userDao.findById(7L).get());
        post.setGroup(grouDao.findById(3L).get());
        post.setHeader(title);
        post.setBody(body);
        post.setPostType(postTypeDao.findById(1L).get());
        Timestamp timestamp = new Timestamp((new Date()).getTime());
        post.setPostDate(timestamp);
        postDao.save(post);
        return "index";
    }



}
