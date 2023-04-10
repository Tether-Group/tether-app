package tethergroup.tether.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.UserRepository;

@Controller
public class UserController {

    private UserRepository userDao;

//    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
//        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/login";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user){
//        String hash = passwordEncoder.encode(user.getPassword());
//        user.setPassword(hash);
//        userDao.save(user);
        return "redirect:/login";
    }


    @GetMapping("/profile")
    public String returnProfilePage() {return "users/profile";}

    @GetMapping("/friends")
    public String returnFriendsListPage() {return "users/friends";}
}
