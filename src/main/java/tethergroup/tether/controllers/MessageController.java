package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.FriendshipRepository;
import tethergroup.tether.repositories.UserRepository;

@RequiredArgsConstructor
@Controller
public class MessageController {
    private final UserRepository userDao;
    private final FriendshipRepository friendshipDao;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/messages")
    public String viewMessages() {
        return "users/messages";
    }

    @GetMapping( "/getUser/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return new ResponseEntity<>(userDao.findByUsername(username), HttpStatus.OK);
    }
}
