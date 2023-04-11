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

    @GetMapping("/posts/search-results")
    public String globalSearch(@RequestParam(name = "search") @Nullable String search, Model model) {
        List<Group> searchedGroups;
        if (search != null) {
            searchedGroups = groupDao.findLikeGroupNameOrDescription(search);
        } else {
            searchedGroups = groupDao.findAll();
        }
        model.addAttribute("searchedGroups", searchedGroups);

        return "posts/search-results";
    }
}
