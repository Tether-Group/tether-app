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
        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            group.setAdmin(loggedInUser);
            groupDao.save(group);
        } catch (Exception e) {
            throw new RuntimeException("cannot create" + e.getMessage());
//            return to redirect error page
        }
        return "redirect:/groups";
    }

    @GetMapping("/group/{groupId}")
    public String addGroupAttributeToGroupPage(Model model, @PathVariable Long groupId) {
        Group group = groupDao.findById(groupId).get();
        User groupCreator = groupDao.findById(groupId).get().getAdmin();
        model.addAttribute("groupCreator", groupCreator);
        model.addAttribute("group", group);

        List<User> members = userDao.findByGroupId(group.getId());
        boolean isMember = false;

        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("loggedInUser", loggedInUser);

            for (User member : members) {
                if (member.getId() == loggedInUser.getId()) {
                    isMember = true;
                    break;
                }
            }
            model.addAttribute("isMember", isMember);
        } catch (Exception e) {
            return "groups/group";
        }
        return "groups/group";
    }

    @PostMapping("/group/edit")
    public String editGroup(@ModelAttribute("group") Group group) {
        Group originalGroup = groupDao.findById(group.getId()).get();
        group.setAdmin(originalGroup.getAdmin());

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User groupAdmin = originalGroup.getAdmin();

//        this is used to ensure that the logged-in user is also the admin of the group page
        if (loggedInUser.getId() != groupAdmin.getId()) {
//          return the 403 page to get out of the method
            System.out.println("Edit not allowed");
            return "redirect:/groups";
        }
        groupDao.save(group);
        return "redirect:/group/" + group.getId();
    }

    @PostMapping("/group/delete")
    public String deleteGroup(@ModelAttribute("group") Group group) {
        Group originalGroup = groupDao.findById(group.getId()).get();

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User groupAdmin = originalGroup.getAdmin();
//      this is used to ensure that the logged-in user is also the admin of the group page
        if (loggedInUser.getId() != groupAdmin.getId()) {
//            return the 403 page to get out of the method
            System.out.println("Delete not allowed");
            return "redirect:/groups";
        }
        groupDao.deleteById(group.getId());
        return "redirect:/groups";
    }

    @GetMapping("group/{groupId}/members")
    public String returnMembersListPage(Model model, @PathVariable Long groupId) {
        List<User> members = userDao.findByGroupId(groupId);
        User admin = groupDao.findById(groupId).get().getAdmin();
        System.out.println(members);
        model.addAttribute("adminMember", admin);
        model.addAttribute("members", members);
        return "groups/members";
    }

    @Transactional
    @PostMapping("/group/{groupId}/join")
    public String requestToJoinGroup(Model model, @PathVariable Long groupId) {
        Group group = groupDao.findById(groupId).get();

        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<User> newMember = group.getMembers();
            newMember.add(loggedInUser);
            group.setMembers(newMember);
        } catch (Exception e) {
            return "redirect:/group/" + group.getId();
        }

        groupDao.save(group);
        return "redirect:/group/" + group.getId();
    }

    @Transactional
    @PostMapping("/group/{groupId}/leave")
    public String leaveGroup(Model model, @PathVariable Long groupId) {
        Group group = groupDao.findById(groupId).get();

        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User originalUser = userDao.findById(loggedInUser.getId()).get();
            List<User> members = userDao.findByGroupId(group.getId());
            members.remove(originalUser);
            group.setMembers(members);
        } catch (Exception e) {
            return "redirect:/group/" + group.getId();
        }

        groupDao.save(group);
        return "redirect:/group/" + group.getId();
    }

}
