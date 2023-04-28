package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tethergroup.tether.models.Friendship;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.FriendshipRepository;
import tethergroup.tether.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MessageController {
    private final UserRepository userDao;
    private final FriendshipRepository friendshioDao;

//    @GetMapping("/messages")
//    public String viewMessages() {
//        return "users/messages";
//    }

    @GetMapping("/messages/talk/{username}")
    public String startNewMessage(Model model, @PathVariable String username) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedInUser", loggedInUser);
        User friend = userDao.findByUsername(username);
        model.addAttribute("friend", friend);
        return "users/messages";
    }

    @GetMapping( "/getUser/{username}")
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return new ResponseEntity<>(userDao.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping(value = "/getUser/loggedInUser", produces = "application/json")
    @ResponseBody
    public User getLoggedInUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping(value = "/getFriends", produces = "application/json")
    @ResponseBody
    public List<User> getLoggedInUsersFriends() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> loggedInUsersFriends = new ArrayList<>();
        List<User> loggedInUsersFriendsWhenUserIsRequester = userDao.getLoggedInUsersFriendsWhenUserIsRequester(loggedInUser.getId());
        List<User> loggedInUsersFriendsWhenUserIsAcceptor = userDao.getLoggedInUsersFriendsWhenUserIsAcceptor(loggedInUser.getId());

        loggedInUsersFriends.addAll(loggedInUsersFriendsWhenUserIsRequester);
        loggedInUsersFriends.addAll(loggedInUsersFriendsWhenUserIsAcceptor);

        return loggedInUsersFriends;
    }
}
