package tethergroup.tether.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tethergroup.tether.models.Group;
import tethergroup.tether.models.PostType;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.GroupRepository;
import tethergroup.tether.repositories.PostTypeRepository;
import tethergroup.tether.repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
public class GroupController {

    private final GroupRepository groupDao;
    private final UserRepository userDao;
    private final PostTypeRepository postTypeDao;


    @GetMapping ("/groups")
    @Transactional
    public String showGroupsListPage(Model model) {
        //        if the viewer is not logged in... show random groups
        List<Group> randomGroups = groupDao.randomGroupsLimitFifty();
        model.addAttribute("randoGroups", randomGroups);

        //        if the user is logged in, show their groups at random
        List<Group> latestGroups = groupDao.groupsByDescendingId();
        model.addAttribute("descGroups", latestGroups);

        List<Group> groups = groupDao.findAll();
        model.addAttribute("groups", groups);
        return "groups/group-list";
    }

    @GetMapping("/group")
    public String showGroupPage() {
        return "groups/group";
    }

    @GetMapping("/group/create")
    public String returnGroupCreatePage() {
        return "groups/create-group";
    }

    @PostMapping("/group/create")
    public String createGroup(
            @RequestParam("group-name") String name,
                              @RequestParam("description") String description,
                              @RequestParam(value = "visibility", required = false) boolean isPrivate,
                              @RequestParam(value = "post-types", required = false) String[] postTypesStrings,
                              Principal principal) {
        List<PostType> postTypes = new ArrayList<>();
        for (String string : postTypesStrings) {
            postTypes.add(postTypeDao.findById((long) Integer.parseInt(string)).get());
        }
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        group.setPrivate(isPrivate);
        group.setPostTypes(postTypes);
        group.setAdmin(userDao.findById(7L).get());
        groupDao.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/group/{groupId}")
    public String addGroupAttributeToGroupPage(Model model, @PathVariable Long groupId) {
        Group group = groupDao.findById(groupId).get();
        model.addAttribute("group", group);
        return "groups/group";
    }

    @GetMapping("group/{groupId}/members")
    public String returnMembersListPage(Model model, @PathVariable Long groupId) {
        List<User> members = userDao.findByGroupId(groupId);
        System.out.println(members);
        model.addAttribute("members", members);
        return "groups/members";
    }
}
