package tethergroup.tether.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tethergroup.tether.models.Group;
import tethergroup.tether.models.Membership;
import tethergroup.tether.models.PostType;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.GroupRepository;
import tethergroup.tether.repositories.MembershipRepository;
import tethergroup.tether.repositories.PostTypeRepository;
import tethergroup.tether.repositories.UserRepository;

import java.util.List;

@AllArgsConstructor
@Controller
public class GroupController {

    private final GroupRepository groupDao;
    private final UserRepository userDao;
    private final PostTypeRepository postTypeDao;
    private final MembershipRepository membershipDao;

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
        Group group = groupDao.findById(groupId).orElse(null);
        if (group == null) {
            return "redirect:/error";
        }
        User groupCreator = groupDao.findById(groupId).get().getAdmin();
        model.addAttribute("groupCreator", groupCreator);
        model.addAttribute("group", group);
        Membership membership = new Membership();
        boolean isMember = true;
        boolean isPending = true;
        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("loggedInUser", loggedInUser);
            membership = membershipDao.findMembershipByUser_IdAndGroup_Id(loggedInUser.getId(), group.getId());

            if (membership == null) {
                model.addAttribute("isMember", false);
                model.addAttribute("isPending", false);
            } else if (membership.isPending()) {
                model.addAttribute("isPending", isPending);
                model.addAttribute("isMember", false);
            }
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
        model.addAttribute("adminMember", admin);
        model.addAttribute("members", members);
        return "groups/members";
    }

    @Transactional
    @PostMapping("/group/{groupId}/join")
    public String requestToJoinGroup(@PathVariable Long groupId) {
        Group group = groupDao.findById(groupId).get();
        Membership newMembership = new Membership();

        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            newMembership.setUser(loggedInUser);
            newMembership.setGroup(group);
            newMembership.setPending(group.isPrivate());
        } catch (Exception e) {
            return "redirect:/group/" + group.getId();
        }

        membershipDao.save(newMembership);
        return "redirect:/group/" + group.getId();
    }

    @Transactional
    @PostMapping("/group/{groupId}/leave")
    public String leaveGroup(@PathVariable Long groupId) {
        Group group = groupDao.findById(groupId).get();
        Membership membership = new Membership();

        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User originalUser = userDao.findById(loggedInUser.getId()).get();

            membership = membershipDao.findMembershipByUser_IdAndGroup_Id(originalUser.getId(), group.getId());

        } catch (Exception e) {
            return "redirect:/group/" + group.getId();
        }

        membershipDao.delete(membership);
        return "redirect:/group/" + group.getId();
    }

}
