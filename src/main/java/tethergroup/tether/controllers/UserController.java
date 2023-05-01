package tethergroup.tether.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.security.core.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tethergroup.tether.models.*;
import tethergroup.tether.repositories.*;

import java.util.ArrayList;
import java.util.List;

import tethergroup.tether.models.Friendship;
import tethergroup.tether.models.PhotoURL;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.FriendshipRepository;
import tethergroup.tether.repositories.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userDao;
    private final FriendshipRepository friendshipDao;
    private final PasswordEncoder passwordEncoder;
    private final MembershipRepository membershipDao;
    private final GroupRepository groupDao;
    private final PostRepository postDao;
    private final CommentRepository commentDao;

    //    display custom login error message
    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model, @ModelAttribute User user) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null && user != null) {
                errorMessage = ex.getMessage();
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "users/login";
    }

    //    creating user
    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("fromRegisterMapping", true);
        return "users/login";
    }

    //    creating user
    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, Model model) {
        User uniqueUsername = userDao.findByUsername(user.getUsername());

        if (uniqueUsername != null) {
            model.addAttribute("usernameExists", true);
            return "users/login";
        }

        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setProfilePhotoUrl("https://cdn.filestackcontent.com/WIu7r67PS0qWdCtNEbIb");
        userDao.save(user);
        return "redirect:/login";
    }

    // viewing profile when logged in
    @Transactional
    @GetMapping("/profile/{username}")
    public String returnProfilePage(Model model, @PathVariable String username) {

        // checks if logged in user is viewing their own profile and redirects them to "/my/account" get mapping
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findByUsername(username);
        if (loggedInUser.getId() == user.getId()) {
            return "redirect:/profile/my-account";
        }
        if (user == null) {
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

        List<Membership> membershipsOfUserOfProfilePage = membershipDao.findMembershipsByUser_IdAndIsPendingIsFalse(profilePageUserId);
        List<Group> groupsWhereUserIsAdmin = groupDao.getAllGroupsByAdminId(user.getId());
        List<Group> allGroupsOfUser = new ArrayList<>(groupsWhereUserIsAdmin);
        for (Membership membership : membershipsOfUserOfProfilePage) {
            Group group = membership.getGroup();
            allGroupsOfUser.add(group);
        }
        List<Group> groups = new ArrayList<>();
        if (allGroupsOfUser.size() < 5) {
            for (int i = 0; i < allGroupsOfUser.size(); i++) {
                groups.add(allGroupsOfUser.get(i));
            }
        } else if (allGroupsOfUser.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                groups.add(allGroupsOfUser.get(i));
            }
        }
        for (Group group : groups) {
            System.out.println(group.getName());
        }
        List<Post> allPostsOfUserOfProfilePage = postDao.findPostsByUser_IdOrderByPostDateDesc(profilePageUserId);
        List<Post> postsOfUserOfProfilePage = new ArrayList<>();
        for (Post post : allPostsOfUserOfProfilePage) {
            if (!post.getGroup().isPrivate()) {
                postsOfUserOfProfilePage.add(post);
            } else if (post.getGroup().isPrivate()) {
                List<Membership> membershipsForGroupOfPost = membershipDao.findAllMembershipsByGroupIdWhereIsNotPending(post.getGroup().getId());
                for (Membership membership : membershipsForGroupOfPost) {
                    if (loggedInUser.getUsername() != null) {
                        if (loggedInUser.getId() == membership.getUser().getId()) {
                            postsOfUserOfProfilePage.add(post);
                        } else if (loggedInUser.getId() == post.getGroup().getAdmin().getId()) {
                            postsOfUserOfProfilePage.add(post);
                        }
                    }
                }
            }
        }
        List<Comment> comments = new ArrayList<>();
        for (Post post : postsOfUserOfProfilePage) {
            System.out.println(post.getPostType().getId());
            List<Comment> commentsOfPost = commentDao.findCommentsByPost_Id(post.getId());
            for (Comment comment : commentsOfPost) {
                comments.add(comment);
            }
        }

        List<Friendship> friendsOfUserOfProfilePage = friendshipDao.getFriendshipsOfUser(profilePageUserId);
        List<User> allFriendsOfUser = new ArrayList<>();
        for (Friendship friendship : friendsOfUserOfProfilePage) {
            User friend = new User();
            if (friendship.getAcceptor().getId() == profilePageUserId && !friendship.isPending()) {
                friend = userDao.findById(friendship.getRequester().getId()).get();
                allFriendsOfUser.add(friend);
            } else if (friendship.getRequester().getId() == profilePageUserId && !friendship.isPending()) {
                friend = userDao.findById(friendship.getAcceptor().getId()).get();
                allFriendsOfUser.add(friend);
            }
        }
        List<User> friends = new ArrayList<>();
        if (allFriendsOfUser.size() < 5) {
            for (int i = 0; i < allFriendsOfUser.size(); i++) {
                friends.add(allFriendsOfUser.get(i));
            }
        } else if (allFriendsOfUser.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                friends.add(allFriendsOfUser.get(i));
            }
        }
        if (postsOfUserOfProfilePage.isEmpty()) {
            model.addAttribute("noPosts", true);
        } else {
            model.addAttribute("noPosts", false);
        }
        if (friends.isEmpty()) {
            model.addAttribute("noFriends", true);
        } else {
            model.addAttribute("noFriends", false);
        }
        if (groups.isEmpty()) {
            model.addAttribute("noGroups", true);
        } else {
            model.addAttribute("noGroups", false);
        }
        model.addAttribute("user", user);
        model.addAttribute("requestExists", friendRequestExists);
        model.addAttribute("isPending", friendRequestIsPending);
        model.addAttribute("hasFriendRequestFromThisUser", loggedInUserHasFriendRequestFromUserOfProfilePage);
        model.addAttribute("isMyAccountPage", false);
        model.addAttribute("groups", groups);
        model.addAttribute("posts", postsOfUserOfProfilePage);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("comments", comments);
        model.addAttribute("friends", friends);
        return "users/profile";
    }

    // my profile tab from navbar
    @GetMapping("/profile/my-account")
    public String returnLoggedInUserProfilePage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> actualUser = userDao.findById(user.getId());
        if (actualUser.isPresent()) {
            User userObj = actualUser.get();
            Long idOfLoggedInUser = userObj.getId();
            model.addAttribute("user", userObj);
            model.addAttribute("isMyAccountPage", true);
            List<Membership> memberships = membershipDao.findMembershipsByUser_IdAndIsPendingIsFalse(idOfLoggedInUser);
            List<Group> groupsWhereUserIsAdmin = groupDao.getAllGroupsByAdminId(idOfLoggedInUser);
            List<Group> allGroupsOfUser = new ArrayList<>(groupsWhereUserIsAdmin);
            for (Membership membership : memberships) {
                Group group = groupDao.findById(membership.getGroup().getId()).get();
                allGroupsOfUser.add(group);
            }
            List<Group> groups = new ArrayList<>();
            if (allGroupsOfUser.size() < 5) {
                for (int i = 0; i < allGroupsOfUser.size(); i++) {
                    groups.add(allGroupsOfUser.get(i));
                }
            } else if (allGroupsOfUser.size() >= 5) {
                for (int i = 0; i < 5; i++) {
                    groups.add(allGroupsOfUser.get(i));
                }
            }

            List<Post> postsOfLoggedInUser = postDao.findPostsByUser_IdOrderByPostDateDesc(idOfLoggedInUser);
            List<Comment> comments = new ArrayList<>();
            for (Post post : postsOfLoggedInUser) {
                System.out.println(post.getPostType().getId());
                List<Comment> commentsOfPost = commentDao.findCommentsByPost_Id(post.getId());
                for (Comment comment : commentsOfPost) {
                    comments.add(comment);
                }
            }
            List<Friendship> friendsOfUserOfProfilePage = friendshipDao.getFriendshipsOfUser(idOfLoggedInUser);
            List<User> allFriendsOfUser = new ArrayList<>();
            for (Friendship friendship : friendsOfUserOfProfilePage) {
                User friend = new User();
                if (friendship.getAcceptor().getId() == idOfLoggedInUser && !friendship.isPending()) {
                    friend = userDao.findById(friendship.getRequester().getId()).get();
                    allFriendsOfUser.add(friend);
                } else if (friendship.getRequester().getId() == idOfLoggedInUser && !friendship.isPending()) {
                    friend = userDao.findById(friendship.getAcceptor().getId()).get();
                    allFriendsOfUser.add(friend);
                }
            }
            List<User> friends = new ArrayList<>();
            if (allFriendsOfUser.size() < 5) {
                for (int i = 0; i < allFriendsOfUser.size(); i++) {
                    friends.add(allFriendsOfUser.get(i));
                }
            } else if (allFriendsOfUser.size() >= 5) {
                for (int i = 0; i < 5; i++) {
                    friends.add(allFriendsOfUser.get(i));
                }
            }
            if (postsOfLoggedInUser.isEmpty()) {
                model.addAttribute("noPosts", true);
            } else {
                model.addAttribute("noPosts", false);
            }
            if (friends.isEmpty()) {
                model.addAttribute("noFriends", true);
            } else {
                model.addAttribute("noFriends", false);
            }
            if (groups.isEmpty()) {
                model.addAttribute("noGroups", true);
            } else {
                model.addAttribute("noGroups", false);
            }
            model.addAttribute("groups", groups);
            model.addAttribute("posts", postsOfLoggedInUser);
            model.addAttribute("loggedInUser", userObj);
            model.addAttribute("comments", comments);
            model.addAttribute("friends", friends);
            // necessary attributes that are used for looking at other profile page
            model.addAttribute("requestExists", true);
            model.addAttribute("isPending", false);
        } else {
            return "redirect:/login";
        }
        return "users/profile";
    }

    @PostMapping("/profile/change-bio")
    public String changeProfileBio(@RequestParam("bio-input") String bio) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> actualUser = userDao.findById(user.getId());
        if (actualUser.isPresent()) {
            User userObj = actualUser.get();
            userObj.setBio(bio);
            userDao.save(userObj);
        } else {
            return "redirect:/login";
        }
        return "redirect:/profile/my-account";
    }

    //    viewing friends list
    @GetMapping("/profile/{username}/friends")
    public String returnFriendsListPage(Model model, @PathVariable String username) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", loggedInUser);

        User user = userDao.findByUsername(username);
        List<Friendship> friendsOfUserOfProfilePage = friendshipDao.getFriendshipsOfUser(user.getId());
        List<User> friends = new ArrayList<>();
        for (Friendship friendship : friendsOfUserOfProfilePage) {
            User friend = new User();
            if (friendship.getAcceptor().getId() == user.getId() && !friendship.isPending()) {
                friend = userDao.findById(friendship.getRequester().getId()).get();
                friends.add(friend);
            } else if (friendship.getRequester().getId() == user.getId() && !friendship.isPending()) {
                friend = userDao.findById(friendship.getAcceptor().getId()).get();
                friends.add(friend);
            }
        }
        model.addAttribute("friends", friends);
        model.addAttribute("userOfFriendsList", user);
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

    @GetMapping("/profile/settings/usernameExists/{attemptedUsername}")
    public String returnSettingsPageAfterUsernameAlreadyExists(Model model, @PathVariable String attemptedUsername) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> actualUser = userDao.findById(user.getId());
        if (actualUser.isPresent()) {
            User userObj = actualUser.get();
            model.addAttribute("user", userObj);
            model.addAttribute("usernameExists", true);
            model.addAttribute("attemptedUsername", attemptedUsername);
        } else {
            return "redirect:/login";
        }
        return "users/edit-user";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute User user, @RequestParam("photo-url") String photoURL) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User actualUser = userDao.findById(loggedInUser.getId()).get();
        String userPassword = userDao.findById(user.getId()).get().getPassword();
        user.setUsername(actualUser.getUsername());
        user.setPassword(userPassword);
        user.setProfilePhotoUrl(photoURL);
        userDao.save(user);
        return "redirect:/profile/my-account";
    }

    @PostMapping("/profile/editpassword")
    public String updatePassword(@RequestParam("oldpassword") String oldPassword, @RequestParam("register-password") String newPassword) {
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

    @PostMapping("/profile/editusername")
    public String updateUsername(@RequestParam("username") String username) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userWithUniqueUsername = userDao.findByUsername(username);

        if (userWithUniqueUsername != null && !userWithUniqueUsername.getUsername().equals(loggedInUser.getUsername())) {
            return "redirect:/profile/settings/usernameExists/" + userWithUniqueUsername.getUsername();
        }
        return "redirect:/profile/my-account";
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
}