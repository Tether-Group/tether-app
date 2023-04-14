package tethergroup.tether.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import tethergroup.tether.models.Group;
import tethergroup.tether.models.Membership;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.GroupRepository;
import tethergroup.tether.repositories.MembershipRepository;
import tethergroup.tether.repositories.PostTypeRepository;
import tethergroup.tether.repositories.UserRepository;

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

    @Transactional
    @GetMapping("/notifications")
    public String showNotifications(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", loggedInUser);

        Long notificationCount = groupDao.getCountOfGroupRequestsForLoggedInUserAndAdmin(loggedInUser.getId());
        model.addAttribute("requestCount", notificationCount);

        Set<User> users = userDao.findUsersFromGroupJoinRequestsForTheAdmin(loggedInUser.getId());
        model.addAttribute("users", users);

        model.addAttribute("needThisToDisplayMemberships",
            users
                .stream()
                .flatMap(u -> u.getMemberships().stream().filter(m -> m.isPending()))
                .collect(Collectors.toSet())
        );

        List<Group> groups = groupDao.findGroupsFromGroupJoinRequestsForTheLoggedInUser(loggedInUser.getId());

        List<Membership> memberships = membershipDao.findMembershipsFromGroupJoinRequestsForTheLoggedInUser(loggedInUser.getId());

        return "users/notifications";
//        return "redirect:/error";
    }
}
