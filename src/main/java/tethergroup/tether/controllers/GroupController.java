package tethergroup.tether.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tethergroup.tether.models.*;
import tethergroup.tether.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class GroupController {

    private final GroupRepository groupDao;
    private final UserRepository userDao;
    private final PostTypeRepository postTypeDao;
    private final MembershipRepository membershipDao;
    private final PostRepository postDao;
    private final CommentRepository commentDao;

    @GetMapping("/groups")
    @Transactional
    public String showGroupsListPage(Model model) {
        //        if the viewer is not logged in... show random groups
//        List<Group> randomGroups = groupDao.randomGroupsLimitFifty();
//        model.addAttribute("randoGroups", randomGroups);

        //        if the user is logged in, show their groups at random
//        List<Group> latestGroups = groupDao.groupsByDescendingId();
//        model.addAttribute("descGroups", latestGroups);

        List<Group> groups = groupDao.findAll();
        model.addAttribute("groups", groups);
        return "groups/group-list";
    }

    @GetMapping("/groups/{username}")
    @Transactional
    public String showGroupsListPage(Model model, @PathVariable String username) {
        User userOfProfilePage = userDao.findByUsername(username);

        List<Membership> memberships = membershipDao.findMembershipsByUser_IdAndIsPendingIsFalse(userOfProfilePage.getId());
        List<Group> groupsWhereUserIsAdmin = groupDao.getAllGroupsByAdminId(userOfProfilePage.getId());
        List<Group> groupsWhereUserIsMember = new ArrayList<>();
        for (Membership membership : memberships) {
            Group group = groupDao.findById(membership.getGroup().getId()).get();
            groupsWhereUserIsMember.add(group);
        }
        model.addAttribute("groupsWhereUserIsAdmin", groupsWhereUserIsAdmin);
        model.addAttribute("groupsWhereUserIsMember", groupsWhereUserIsMember);
        model.addAttribute("user", userOfProfilePage);
        return "groups/users-groups";
    }

    @GetMapping("/profile/my-account/groups")
    @Transactional
    public String showMyGroupsPage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> actualUser = userDao.findById(user.getId());
        if (actualUser.isPresent()) {
            User userObj = actualUser.get();
            List<Membership> memberships = membershipDao.findMembershipsByUser_IdAndIsPendingIsFalse(userObj.getId());
            List<Group> groupsWhereUserIsAdmin = groupDao.getAllGroupsByAdminId(userObj.getId());
            List<Group> groupsWhereUserIsMember = new ArrayList<>();
            for (Membership membership : memberships) {
                Group group = groupDao.findById(membership.getGroup().getId()).get();
                groupsWhereUserIsMember.add(group);
            }
            model.addAttribute("groupsWhereUserIsAdmin", groupsWhereUserIsAdmin);
            model.addAttribute("groupsWhereUserIsMember", groupsWhereUserIsMember);
        } else {
            return "redirect:/login";
        }
        return "groups/my-groups";
    }

    @GetMapping("/group/create")
    public String returnGroupCreatePage(Model model) {
        model.addAttribute("group", new Group());
        return "groups/create-group";
    }

//    @PostMapping("/group/create")
//    public String createGroup(@ModelAttribute("group") Group group, @RequestParam("photo-url") @Nullable String photoURL, @RequestParam("visibility") boolean isPrivate, @RequestParam("postTypeTwo") @Nullable Long eventPostType, @RequestParam("postTypeThree") @Nullable Long forSalePostType, @RequestParam("postTypeFour") @Nullable Long qAndAPostType) {
//        try {
//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Optional<User> actualUser = userDao.findById(user.getId());
//            if (actualUser.isPresent()) {
//                User userObj = actualUser.get();
//                List<PostType> postTypesForGroup = new ArrayList<>();
//                postTypesForGroup.add(postTypeDao.findById(1L).get());
//                if (eventPostType != null) {
//                    postTypesForGroup.add(postTypeDao.findById(2L).get());
//                }
//                if (forSalePostType != null) {
//                    postTypesForGroup.add(postTypeDao.findById(3L).get());
//                }
//                if (qAndAPostType != null) {
//                    postTypesForGroup.add(postTypeDao.findById(4L).get());
//                }
//                group.setPostTypes(postTypesForGroup);
//                group.setAdmin(userObj);
//                if (!photoURL.equals("")) {
//                    group.setGroupPhotoURL(photoURL);
//                } else {
//                    group.setGroupPhotoURL("https://cdn.filestackcontent.com/srWrNqvTyCSUHB3OmPiA");
//                }
//                group.setPrivate(isPrivate);
//                Long newGroupId = groupDao.save(group).getId();
//                return "redirect:/group/" + newGroupId;
//            } else {
//                return "redirect:/login";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException();
//        }
//    }

    @PostMapping("/group/create")
    public String createGroup(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("group-photo-url") @Nullable String photoURL, @RequestParam("visibility") boolean isPrivate, @RequestParam("postTypeTwo") @Nullable Long eventPostType, @RequestParam("postTypeThree") @Nullable Long forSalePostType, @RequestParam("postTypeFour") @Nullable Long qAndAPostType) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<User> actualUser = userDao.findById(user.getId());
            if (actualUser.isPresent()) {
                Group group = new Group();
                User userObj = actualUser.get();
                group.setName(name);
                group.setDescription(description);
                List<PostType> postTypesForGroup = new ArrayList<>();
                postTypesForGroup.add(postTypeDao.findById(1L).get());
                if (eventPostType != null) {
                    postTypesForGroup.add(postTypeDao.findById(2L).get());
                }
                if (forSalePostType != null) {
                    postTypesForGroup.add(postTypeDao.findById(3L).get());
                }
                if (qAndAPostType != null) {
                    postTypesForGroup.add(postTypeDao.findById(4L).get());
                }
                group.setPostTypes(postTypesForGroup);
                group.setAdmin(userObj);
                if (!photoURL.equals("")) {
                    group.setGroupPhotoURL(photoURL);
                } else {
                    group.setGroupPhotoURL("https://cdn.filestackcontent.com/srWrNqvTyCSUHB3OmPiA");
                }
                group.setPrivate(isPrivate);
                Long newGroupId = groupDao.save(group).getId();
                return "redirect:/group/" + newGroupId;
            } else {
                return "redirect:/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @GetMapping("/group/{groupId}")
    public String addGroupAttributeToGroupPage(Model model, @PathVariable Long groupId, @ModelAttribute("remove-member-success") String flashAttrRemoveMemberSuccess) {
        Group group = groupDao.findById(groupId).orElse(null);
        if (group == null) {
            return "redirect:/error";
        }
        boolean groupIsPrivate = group.isPrivate();
        model.addAttribute("groupIsPrivate", groupIsPrivate);
        Membership membershipOfLoggedInUser = new Membership();
        try {
            // does this stuff if user is logged in
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("loggedInUser", loggedInUser);
            membershipOfLoggedInUser = membershipDao.findMembershipByUser_IdAndGroup_Id(loggedInUser.getId(), group.getId());

            if (membershipOfLoggedInUser == null) {
                // no membership request
                model.addAttribute("isMember", false);
                model.addAttribute("isPending", false);
            } else if (membershipOfLoggedInUser.isPending()) {
                // membership request exists and is pending
                model.addAttribute("isPending", true);
                model.addAttribute("isMember", false);
            } else {
                //membership request exists and is NOT pending
                model.addAttribute("isPending", false);
                model.addAttribute("isMember", true);
            }
            User admin = groupDao.findById(groupId).get().getAdmin();
            if (loggedInUser.getId() == admin.getId()) {
                model.addAttribute("isAdmin", true);
                model.addAttribute("flashAttrRemoveMemberSuccess", flashAttrRemoveMemberSuccess);
            } else {
                model.addAttribute("isAdmin", false);
            }
            List<Post> posts = postDao.findByGroup_IdOrderByPostDateDesc(group.getId());
            List<PostType> postTypes = postTypeDao.findAll();
            List<PostType> postTypesIdsOfGroup = group.getPostTypes();
            List<Long> postTypeIdsOfGroup = new ArrayList<>();
            List<Membership> memberships = membershipDao.findMembershipsByGroupIdWhereIsNotPendingLimitFive(groupId);
            List<User> members = new ArrayList<>();
            for (Membership membershipOfGroup : memberships) {
                User member = userDao.findById(membershipOfGroup.getUser().getId()).get();
                members.add(member);
            }
            List<Comment> comments = commentDao.findCommentsByGroup_IdOrderByCommentDateDesc(groupId);
            for (Comment comment : comments) {
                System.out.println(comment.getPost().getId());
            }
            for (PostType postType : postTypesIdsOfGroup) {
                postTypeIdsOfGroup.add(postType.getId());
            }

            for (int i = 0; i < postTypes.size(); i++) {
                model.addAttribute("postType" + (i + 1) + "Id", postTypes.get(i).getId());
            }

            model.addAttribute("postTypeIdsOfGroup", postTypeIdsOfGroup);
            model.addAttribute("admin", admin);
            model.addAttribute("group", group);
            model.addAttribute("posts", posts);
            model.addAttribute("members", members);
            model.addAttribute("comments", comments);
        } catch (Exception e) {
            // does this instead if user is not logged in
            if (group.isPrivate()) {
                return "redirect:/login";
            }
            User admin = groupDao.findById(groupId).get().getAdmin();
            List<Post> posts = postDao.findByGroup_IdOrderByPostDateDesc(group.getId());
            List<PostType> postTypes = postTypeDao.findAll();
            List<PostType> postTypesIdsOfGroup = group.getPostTypes();
            List<Long> postTypeIdsOfGroup = new ArrayList<>();
            List<User> members = userDao.findByGroupIdLimitFive(groupId);
            List<Comment> comments = commentDao.findCommentsByGroup_IdOrderByCommentDateDesc(groupId);
            for (Comment comment : comments) {
                System.out.println(comment.getPost().getId());
            }
            for (PostType postType : postTypesIdsOfGroup) {
                postTypeIdsOfGroup.add(postType.getId());
            }
            for (int i = 0; i < postTypes.size(); i++) {
                model.addAttribute("postType" + (i + 1) + "Id", postTypes.get(i).getId());
            }

            model.addAttribute("postTypeIdsOfGroup", postTypeIdsOfGroup);
            model.addAttribute("admin", admin);
            model.addAttribute("group", group);
            model.addAttribute("posts", posts);
            model.addAttribute("members", members);
            model.addAttribute("comments", comments);
            model.addAttribute("isAdmin", false);
        }
        return "groups/group";
    }

    @PostMapping("/group/edit")
    public String editGroup(@ModelAttribute("group") Group group, @RequestParam("edit-group-photo-url") @Nullable String photoURL, @RequestParam("visibility") boolean isPrivate, @RequestParam("postTypeTwo") @Nullable Long eventPostType, @RequestParam("postTypeThree") @Nullable Long forSalePostType, @RequestParam("postTypeFour") @Nullable Long qAndAPostType) {
        Group originalGroup = groupDao.findById(group.getId()).get();
        group.setAdmin(originalGroup.getAdmin());

        List<PostType> postTypesForGroup = new ArrayList<>();
        postTypesForGroup.add(postTypeDao.findById(1L).get());

        if (eventPostType != null) {
            postTypesForGroup.add(postTypeDao.findById(2L).get());
        }
        if (forSalePostType != null) {
            postTypesForGroup.add(postTypeDao.findById(3L).get());
        }
        if (qAndAPostType != null) {
            postTypesForGroup.add(postTypeDao.findById(4L).get());
        }
        group.setPostTypes(postTypesForGroup);
        if (photoURL != null) {
            group.setGroupPhotoURL(photoURL);
        }
        group.setPrivate(isPrivate);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User groupAdmin = originalGroup.getAdmin();

//        this is used to ensure that the logged-in user is also the admin of the group page
        if (loggedInUser.getId() != groupAdmin.getId()) {
//          return the 403 page to get out of the method
            System.out.println("Edit not allowed");
            return "redirect:/groups";
        }
        groupDao.save(group);
        if (group.isPrivate() == false) {
            List<Membership> membershipsStillPendingForGroup = membershipDao.findMembershipsByGroup_IdAndIsPendingIsTrue(group.getId());
            for (Membership membership : membershipsStillPendingForGroup) {
                membership.setPending(false);
                membershipDao.save(membership);
            }
        }
        return "redirect:/group/" + group.getId();
    }

    @PostMapping("/group/delete")
    public String deleteGroup(@ModelAttribute("group") Group group) {
        Group originalGroup = groupDao.findById(group.getId()).get();

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User groupAdmin = originalGroup.getAdmin();
//      this is used to ensure that the logged-in user is also the admin of the group page
        if (loggedInUser.getId() != groupAdmin.getId()) {
            System.out.println("Delete not allowed");
            return "redirect:/error";
        }
        groupDao.deleteById(group.getId());
        return "redirect:/profile/my-account/groups";
    }

    @GetMapping("group/{groupId}/members")
    public String returnMembersListPage(Model model, @PathVariable Long groupId) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User admin = groupDao.findById(groupId).get().getAdmin();
        boolean loggedInUserIsGroupAdmin = loggedInUser.getId() == admin.getId();
        model.addAttribute("loggedInUserIsAdmin", loggedInUserIsGroupAdmin);
        List<Membership> memberships = membershipDao.findAllMembershipsByGroupIdWhereIsNotPending(groupId);
        List<User> members = new ArrayList<>();
        for (Membership membership : memberships) {
            User member = userDao.findById(membership.getUser().getId()).get();
            members.add(member);
        }

        Group thisGroup = groupDao.findById(groupId).get();

        model.addAttribute("thisGroup", thisGroup);
        model.addAttribute("adminMember", admin);
        model.addAttribute("members", members);
        return "groups/members";
    }

    //    @Transactional
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

    //    @Transactional
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

    @PostMapping("/group/{groupId}/{memberId}/remove")
    public String removeMemberFromGroup(@PathVariable Long groupId, @PathVariable Long memberId, RedirectAttributes redirectAttributes) {
        Group group = groupDao.findById(groupId).get();
        Membership membership = membershipDao.findMembershipByUser_IdAndGroup_Id(memberId, group.getId());
        membershipDao.delete(membership);
        redirectAttributes.addFlashAttribute("remove-member-success", "YOU HAVE SUCCESSFULLY REMOVED  @" + membership.getUser().getUsername() + "  FROM YOUR GROUP!");
        return "redirect:/group/" + group.getId();
    }

}