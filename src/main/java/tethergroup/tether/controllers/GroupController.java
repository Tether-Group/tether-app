package tethergroup.tether.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import tethergroup.tether.models.Group;
import tethergroup.tether.repositories.GroupRepository;
import java.util.List;

@AllArgsConstructor
@Controller
public class GroupController {

    private final GroupRepository groupDao;

    @GetMapping ("/groups")
    public String showGroupsListPage(Model model) {
//        if the viewer is not logged in... show random groups
        List<Group> randomGroups = groupDao.randomGroups();
        model.addAttribute("randoGroups", randomGroups);

//        if the user is logged in, show their groups at random
        List<Group> latestGroups = groupDao.groupsByDescendingId();
        model.addAttribute("descGroups", latestGroups);
        return "groups/group-list";
    }

    @GetMapping("/search")
    public String groupsSearched(Model model){
        List<Group>searchedGroups = groupDao.groupsSearched();
        model.addAttribute("searchedGroups", searchedGroups);
//        TODO: Insert location for searched groups below
        return "groups/index";
    }

    @GetMapping("/group")
    public String returnGroupPage() {
        return "groups/group";
    }

    @GetMapping("/members")
    public String returnMembersListPage() {return "groups/members";}
}
