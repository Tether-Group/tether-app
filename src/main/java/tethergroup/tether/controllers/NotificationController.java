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

        Long groupRequestCount = groupDao.getCountOfGroupRequestsForLoggedInUserAndAdmin(loggedInUser.getId());

        Long friendRequestCount = friendshipDao.getCountOfFriendRequestsForLoggedInUser(loggedInUser.getId());
        Long notificationCount = groupRequestCount + friendRequestCount;
        model.addAttribute("requestCount", notificationCount);

        Set<User> users = userDao.findUsersFromGroupJoinRequestsForTheAdmin(loggedInUser.getId());
        model.addAttribute("users", users);

        List<Friendship> friendshipRequests = friendshipDao.getFriendshipRequestsForLoggedInUser(loggedInUser.getId());
        for (Friendship friendshipRequest : friendshipRequests) {
            System.out.println(friendshipRequest.getRequester());
        }
        model.addAttribute("friendshipRequests", friendshipRequests);

//       the code first converts the users collection to a stream, then maps each User object to a stream of its pending
//       Membership objects (using flatMap()), and collects all the pending Membership objects into a Set
//       using the collect() method with the toSet() collector from the Collectors class. The resulting set is then
//       added as an attribute to the model object to store it in the model.
        model.addAttribute("needThisToDisplayMemberships",
            users
                .stream()
                .flatMap(u -> u.getMemberships().stream().filter(m -> m.isPending()))
                .collect(Collectors.toSet())
        );

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
