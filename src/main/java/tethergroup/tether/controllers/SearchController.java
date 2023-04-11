package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tethergroup.tether.models.Group;
import tethergroup.tether.models.Post;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.GroupRepository;
import tethergroup.tether.repositories.PostRepository;
import tethergroup.tether.repositories.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class SearchController {

    private PostRepository postDao;
    private UserRepository userDao;
    private GroupRepository groupDao;

    @GetMapping("/search")
    public String GlobalSearch(Model model) {
        List<Post> searchedPosts = postDao.postsSearched();
        model.addAttribute("searchedPosts", searchedPosts);

        List<Group> searchedGroups = groupDao.groupsSearched();
        model.addAttribute("searchedGroups", searchedGroups);

        List<User> searchedUsers = userDao.usersSearched();
        model.addAttribute("searchedUsers", searchedUsers);

//        TODO: Insert location for searched posts below
        return "posts/search-results";
    }
}
