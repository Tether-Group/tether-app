package tethergroup.tether.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tethergroup.tether.models.Friendship;
import tethergroup.tether.models.PhotoURL;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.FriendshipRepository;
import tethergroup.tether.repositories.UserRepository;
import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userDao;
    private final FriendshipRepository friendshipDao;
    private final PasswordEncoder passwordEncoder;


    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, FriendshipRepository friendshipDao) {
        this.userDao = userDao;
        this.friendshipDao = friendshipDao;
        this.passwordEncoder = passwordEncoder;
    }


    //    creating user
    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "users/login";
    }

    //    creating user
    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }


    // viewing profile when logged in
    @GetMapping("/profile/{username}")
    public String returnProfilePage(Model model, @PathVariable String username) {

        // checks if logged in user is viewing their own profile and redirects them to "/my/account" get mapping
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findByUsername(username);
        if (loggedInUser.getId() == user.getId()) {
            return "redirect:/profile/my-account";
        } else if (user == null) {
            return "redirect:/error";
        }
        boolean friendRequestExists = false;
        boolean friendRequestIsPending = false;
        boolean loggedInUserHasFriendRequestFromUserOfProfilePage = false;

        Long loggedInUserId = loggedInUser.getId();
        Long profilePageUserId = user.getId();

        Friendship friendRequest = friendshipDao.findByRequester_IdAndAcceptor_Id(loggedInUserId, profilePageUserId);
        // if there is no friend request from loggedInUser to userOfProfilePage
        if (friendRequest == null) {
            Friendship userOfProfilePageFriendRequestToLoggedInUser = friendshipDao.findByRequester_IdAndAcceptor_Id(profilePageUserId, loggedInUserId);
            // if there is a friend request from userOfProfilePage to loggedInUser and it is pending
            if (userOfProfilePageFriendRequestToLoggedInUser != null && userOfProfilePageFriendRequestToLoggedInUser.isPending()) {
                friendRequestExists = true;
                friendRequestIsPending = true;
                loggedInUserHasFriendRequestFromUserOfProfilePage = true;
                // if there is friend request from userOfProfilePage and it is not pending
            } else if (userOfProfilePageFriendRequestToLoggedInUser != null && !userOfProfilePageFriendRequestToLoggedInUser.isPending()) {
                friendRequestExists = true;
                friendRequestIsPending = false;
                loggedInUserHasFriendRequestFromUserOfProfilePage = true;
            } else {
                friendRequestExists = false;
                friendRequestIsPending = false;
                loggedInUserHasFriendRequestFromUserOfProfilePage = false;
            }
        } else if (friendRequest.isPending()) {
            friendRequestExists = true;
            friendRequestIsPending = true;
        } else {
            friendRequestExists = true;
        }

        model.addAttribute("user", user);
        model.addAttribute("requestExists", friendRequestExists);
        model.addAttribute("isPending", friendRequestIsPending);
        model.addAttribute("hasFriendRequestFromThisUser", loggedInUserHasFriendRequestFromUserOfProfilePage);
        model.addAttribute("isMyAccountPage", false);
        return "users/profile";
    }

    // my profile tab from navbar
    @GetMapping("/profile/my-account")
    public String returnLoggedInUserProfilePage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> actualUser = userDao.findById(user.getId());
        if (actualUser.isPresent()) {
            User userObj = actualUser.get();
            model.addAttribute("user", userObj);
            model.addAttribute("isMyAccountPage", true);
        } else {
            return "redirect:/login";
        }
        return "users/profile";
    }



    //    viewing friends list
    @GetMapping("/friends")
    public String returnFriendsListPage() {
        return "users/friends";
    }


    //   view settings page
    @GetMapping("/profile/settings")
    public String returnSettingsPage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> actualUser = userDao.findById(user.getId());
        if (actualUser.isPresent()) {
            User userObj = actualUser.get();
            model.addAttribute("user", userObj);
        } else {
            return "redirect:/login";
        }
        return "users/edit-user";
    }



    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute User user, HttpSession session) {
        System.out.println(user.getId());
        String userPassword = userDao.findById(user.getId()).get().getPassword();
        user.setPassword(userPassword);
        userDao.save(user);
        return "redirect:/";
    }

    @PostMapping("/profile/editpassword")
    public String updatePassword(@RequestParam ("oldpassword") String oldPassword, @RequestParam ("register-password") String newPassword){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String oldPasswordFromDataBase = userDao.findById(loggedInUser.getId()).get().getPassword();
        boolean doesMatch = passwordEncoder.matches(oldPassword, oldPasswordFromDataBase);

        if (!doesMatch) {
            return "redirect:/profile/settings";
        } else {
            User user = userDao.findById(loggedInUser.getId()).get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userDao.save(user);
            return "redirect:/";
        }
    }



    @PostMapping("/profile/delete")
    public String deleteAccount() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User actualUser = userDao.findById(loggedInUser.getId()).get();
        userDao.delete(actualUser);
        SecurityContextHolder.clearContext();
        return "redirect:/my/logout";
    }


    @GetMapping("/my/logout")
    public String manualLogout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

    @PostMapping(value = "/add-profile-photo", consumes = "application/json")
    public String addProfilePhoto(@RequestBody PhotoURL profilePhotoURL) {
        System.out.println(profilePhotoURL.getPhotoURL());
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User actualUser = userDao.findById(loggedInUser.getId()).get();
        actualUser.setProfilePhotoUrl(profilePhotoURL.getPhotoURL());
        userDao.save(actualUser);
        return "redirect:/profile/" + actualUser.getUsername();
    }
}