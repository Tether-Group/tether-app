package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final GroupRepository groupDao;

    @GetMapping("/search-results")
    public String globalSearch(@RequestParam(name = "search") @Nullable String search, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", loggedInUser);
        List<Group> searchedGroups;
        List<Post> searchedPosts;
        List<User> searchedUsers;
        if (search != null) {
            searchedGroups = groupDao.findLikeGroupNameOrDescription(search);
            searchedPosts = postDao.findLikePostNameOrHeaderOrBody(search);
            searchedUsers = userDao.findLikeUsername(search);
        } else {
            searchedGroups = groupDao.findAll();
            searchedPosts = postDao.findAll();
            searchedUsers = userDao.findAll();
        }
        model.addAttribute("searchedGroups", searchedGroups);
        model.addAttribute("searchedPosts", searchedPosts);
        model.addAttribute("searchedUsers", searchedUsers);

        return "posts/search-results";
    }
}
