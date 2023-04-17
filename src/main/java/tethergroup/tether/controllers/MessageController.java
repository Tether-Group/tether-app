package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
