package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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

    private PostRepository postDao;
    private UserRepository userDao;
    private GroupRepository groupDao;

    @GetMapping("/search-results")
    public String globalGETSearch(Model model, @RequestParam(name = "global-search") String group) {
        return "posts/search-results";
    }

    @PostMapping("/search-results")
    public String globalPOSTSearch(Model model, @RequestParam(name = "global-search") String group) {
//        List<Group> searchedGroups = groupDao.searchByGroupNameLike(group);
//        model.addAttribute("searchedGroups", searchedGroups);

        return "posts/search-results";
    }
}
