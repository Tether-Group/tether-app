package tethergroup.tether.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import tethergroup.tether.models.Friendship;
import tethergroup.tether.models.Group;
import tethergroup.tether.models.Membership;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Controller
public class NotificationController {

    private final GroupRepository groupDao;
    private final UserRepository userDao;
    private final PostTypeRepository postTypeDao;
    private final MembershipRepository membershipDao;
    private final FriendshipRepository friendshipDao;

    @Transactional
    @GetMapping("/notifications")
    public String showNotifications(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", loggedInUser);

//        getting the notification count
        Long groupRequestCount = groupDao.getCountOfGroupRequestsForLoggedInUserAndAdmin(loggedInUser.getId());

//        getting the friend requests for the logged in user to accept/deny
        Long friendRequestCount = friendshipDao.getCountOfFriendRequestsForLoggedInUser(loggedInUser.getId());
        Long notificationCount = groupRequestCount + friendRequestCount;
        model.addAttribute("requestCount", notificationCount);

        Set<User> users = userDao.findUsersFromGroupJoinRequestsForTheAdmin(loggedInUser.getId());
        model.addAttribute("users", users);

        List<Friendship> friendshipRequests = friendshipDao.getFriendshipRequestsForLoggedInUser(loggedInUser.getId());
        for (Friendship friendshipRequest : friendshipRequests) {
            System.out.println(friendshipRequest.getRequester());
        }
        System.out.println(friendshipRequests);
        model.addAttribute("friendshipRequests", friendshipRequests);

//        getting the pending group requests for the logged in user to accept/deny
        List<Membership> membershipRequests = membershipDao.findMembershipsFromGroupJoinRequestsForTheLoggedInUser(loggedInUser.getId());
        for (Membership membershipRequest : membershipRequests) {
            System.out.println(membershipRequest.getUser().getId());
        }
        System.out.println(membershipRequests);
        model.addAttribute("membershipRequests", membershipRequests);

        return "users/notifications";
//        return "redirect:/error";
    }

    @Transactional
    @PostMapping("/notifications/deny/{id}")
    public String denyGroupRequest(@PathVariable("id") Long membershipId) {
        try {
            Membership membershipToDelete = membershipDao.findById(membershipId).get();
            membershipDao.delete(membershipToDelete);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:/notifications";
    }

    @PostMapping("/notifications/accept/{id}")
    public String acceptGroupRequest(@PathVariable("id") Long membershipId) {
        Membership membershipToAccept = membershipDao.findById(membershipId).get();
        membershipToAccept.setPending(false);
        membershipDao.save(membershipToAccept);
        return "redirect:/notifications";
    }

    @GetMapping(value = "/getNotificationCount", produces = "application/json")
    @ResponseBody
    public Long getNotificationCount() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long groupRequestCount = groupDao.getCountOfGroupRequestsForLoggedInUserAndAdmin(loggedInUser.getId());
        Long friendRequestCount = friendshipDao.getCountOfFriendRequestsForLoggedInUser(loggedInUser.getId());
        Long notificationCount = groupRequestCount + friendRequestCount;
        return notificationCount;
    }
}
