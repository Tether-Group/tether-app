package tethergroup.tether.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import tethergroup.tether.models.Membership;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.GroupRepository;
import tethergroup.tether.repositories.MembershipRepository;
import tethergroup.tether.repositories.PostTypeRepository;
import tethergroup.tether.repositories.UserRepository;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class NotificationController {

    private final GroupRepository groupDao;
    private final UserRepository userDao;
    private final PostTypeRepository postTypeDao;
    private final MembershipRepository membershipDao;

    //    TODO: * For admins, check the memberships table, see if they have any pending requests, get the count of those
    //     requests for those groups that they are the admin of, and display them on the page if they are logged in, if
    //     they are the admin.Then, for each request, display forms for accept/deny which will make the user join/reject
    //     the membership based on the admin’s decision.

    @GetMapping("/notifications")
    public String showNotifications(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", loggedInUser);

        List<User> userRequests = userDao.findAdminsPendingGroupRequestsCount(loggedInUser.getId());
        System.out.println(userRequests);

        return "users/notifications";
//        return "redirect:/error";
    }
}
