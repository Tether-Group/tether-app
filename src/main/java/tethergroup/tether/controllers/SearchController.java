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
import tethergroup.tether.models.Membership;
import tethergroup.tether.models.Post;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.GroupRepository;
import tethergroup.tether.repositories.MembershipRepository;
import tethergroup.tether.repositories.PostRepository;
import tethergroup.tether.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class SearchController {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final GroupRepository groupDao;
    private final MembershipRepository membershipDao;

    @GetMapping("/search-results")
    public String globalSearch(@RequestParam(name = "search") @Nullable String search, Model model) {
        User loggedInUser = new User();
        try {
            loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("loggedInUser", loggedInUser);
        } catch (Exception e) {
            System.out.println("User is not logged in");
        }
        List<Group> searchedGroups;
        List<Post> allSearchedPosts;
        List<User> searchedUsers;
        if (search != null) {
            searchedGroups = groupDao.findLikeGroupNameOrDescription(search);
            allSearchedPosts = postDao.findLikePostNameOrHeaderOrBody(search);
            searchedUsers = userDao.findLikeUsername(search);
        } else {
            searchedGroups = groupDao.findAll();
            allSearchedPosts = postDao.findAllByOrderByPostDateDesc();
            searchedUsers = userDao.findAll();
        }
        List<Post> searchedPosts = new ArrayList<>();
        for (Post post : allSearchedPosts) {
            if (!post.getGroup().isPrivate()) {
                searchedPosts.add(post);
            } else if (post.getGroup().isPrivate()) {
                List<Membership> membershipsForGroupOfPost = membershipDao.findAllMembershipsByGroupIdWhereIsNotPending(post.getGroup().getId());
                for (Membership membership : membershipsForGroupOfPost) {
                    if (loggedInUser.getUsername() != null) {
                        if (loggedInUser.getId() == membership.getUser().getId()) {
                            searchedPosts.add(post);
                        } else if (loggedInUser.getId() == post.getGroup().getAdmin().getId()) {
                            searchedPosts.add(post);
                        }
                    }
                }
            }
        }
        model.addAttribute("searchedGroups", searchedGroups);
        model.addAttribute("searchedPosts", searchedPosts);
        model.addAttribute("searchedUsers", searchedUsers);

        return "posts/search-results";
    }
}
