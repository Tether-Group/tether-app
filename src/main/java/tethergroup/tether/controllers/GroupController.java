package tethergroup.tether.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/group/create")
    public String returnGroupCreatePage(Model model) {
        model.addAttribute("group", new Group());
        return "groups/create-group";
    }

    @PostMapping("/group/create")
    public String createGroup(@ModelAttribute("group") Group group) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        group.setAdmin(loggedInUser);
        groupDao.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/group/{groupId}")
    public String addGroupAttributeToGroupPage(Model model, @PathVariable Long groupId) {
        Group group = groupDao.findById(groupId).get();
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User groupCreator = groupDao.findById(groupId).get().getAdmin();

        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("groupCreator", groupCreator);
        model.addAttribute("group", group);
        return "groups/group";
    }

    @PostMapping("/group/edit")
    public String editGroup(@ModelAttribute("group") Group group) {
        Group originalGroup = groupDao.findById(group.getId()).get();
        group.setAdmin(originalGroup.getAdmin());
        groupDao.save(group);
        return "redirect:/group/" + group.getId();
    }

    @PostMapping("/group/delete")
    public String deleteGroup(@ModelAttribute("group") Group group) {
        groupDao.deleteById(group.getId());
        return "redirect:/groups";
    }

    @GetMapping("group/{groupId}/members")
    public String returnMembersListPage(Model model, @PathVariable Long groupId) {
        List<User> members = userDao.findByGroupId(groupId);
        System.out.println(members);
        model.addAttribute("members", members);
        return "groups/members";
    }
}
